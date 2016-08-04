package engine;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.PrintusTrouble;
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

    public Map<String,Printer> getPrintersInfo() throws PrintusTrouble {
        //SNMP init and configure
        SnmpQuerier snmpquerier = new SnmpQuerier();
        ObjectMapper m = new ObjectMapper();

        Map<String, PrinterTemplate> ptempmap = new HashMap<>();    //temporary map
        Map<String, Printer> pmap = new HashMap<>();                //final map

        //get ip from ip.txt
        List<String> iplist = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(realpath + "/" + "ip.txt"));
            while (in.hasNextLine()) iplist.add(in.nextLine());
        } catch (FileNotFoundException e) {
            log.error("IP.txt not found!");
            throw new PrintusTrouble("Text file with ip not found; "+e);
        }
        //create template object        //return map
        try {
            JsonNode root = m.readTree(new File(realpath +"/"+"config.json"));
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
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error("JSON config file not found!");
            throw new PrintusTrouble("JSON config file not found!" + e);
        }

        for (String ip : iplist) {
            try {
                try {
                    snmpquerier.start();
                    String pmodel = snmpquerier.send(ip, "1.3.6.1.2.1.25.3.2.1.3.1");
                    Printer p = new Printer(ptempmap.get(pmodel), ip, snmpquerier);
                    p.recognize();
                    pmap.put(ip, p);
                              } finally {
                                snmpquerier.stop();
                }
            } catch (IOException e) {
                log.error("Somethings went wrong with connect to device - "+ip);
                throw new PrintusTrouble("Somethings went wrong with connect to device");
            }
        }
        return pmap;
    }

}
