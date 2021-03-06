package engine;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by tokido on 6/14/16.
 */
public class Engine {

    public Engine(String realpath) {
        this.realpath = realpath;
    }

    private static final Logger log = LoggerFactory.getLogger(Engine.class);
    private String realpath;

    public Map<String, Printer> getPrintersInfo() throws IOException {
        SnmpQuerier snmpquerier = new SnmpQuerier();
        ObjectMapper m = new ObjectMapper();
        Map<String, PrinterTemplate> ptempmap = new HashMap<>();    //temporary map
        Map<String, Printer> pmap = new HashMap<>();                //final map
        List<String> iplist = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(realpath + "/" + "ip.txt"));
            while (in.hasNextLine()) iplist.add(in.nextLine());
        } catch (FileNotFoundException e) {
            log.error("IP.txt not found!");
        }

        try {
            JsonNode root = m.readTree(new File(realpath + "/" + "config.json"));
            JsonNode secondroot = root.path("Printers");
            for (JsonNode node : secondroot) {
                PrinterTemplate printerTemplate = new PrinterTemplate();
                printerTemplate.setModel(node.path("desc").asText());
                JsonNode oidroot = node.path("oid");
                Iterator<String> fieldNames = oidroot.fieldNames();
                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    String fieldValue = oidroot.get(fieldName).asText();
                    printerTemplate.setParameters(fieldName, fieldValue);
                }
                ptempmap.put(printerTemplate.getModel(), printerTemplate);
            }
        } catch (JsonGenerationException e) {
            log.error("Critical json error.", e);
        } catch (JsonProcessingException e) {
            log.error("Json processing error.", e);
        } catch (IOException e) {
            log.error("JSON config file not f ound!");
        }

        for (String ip : iplist) {
            try {
                snmpquerier.start();
                String pmodel = snmpquerier.send(ip, "1.3.6.1.2.1.25.3.2.1.3.1");
                if (!modelExists(ip, pmodel) || !templateExists(ptempmap, pmodel, ip)) {
                    continue;
                }
                Printer p = new Printer(ptempmap.get(pmodel), ip, snmpquerier);
                p.recognize();
                pmap.put(ip, p);
                log.debug("Looks like all good with " + ip + " " + "[" + pmodel + "].");
            } catch (IOException e) {
                log.error("Somethings went wrong with connect to device - " + ip + " \n" + e);
            } finally {
                snmpquerier.stop();
            }
        } //end for loop
        return pmap;
    }

    private boolean templateExists(Map<String, PrinterTemplate> ptempmap, String pmodel, String ip) {
        if (!ptempmap.containsKey(pmodel)) {
            log.error("Device with " + ip + ", not found in config.json.");
            return false;
        }
        return true;
    }

    private boolean modelExists(String ip, String pmodel) {
        if (pmodel == null) {
            log.error("Printer with " + ip + "; do not answer. Drop his.");
            return false;
        }
        return true;
    }

}
