package engine;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by tokido on 6/14/16.
 */
public class Engine {

    public Engine(String realpath) {
        this.realpath = realpath;
    }

    private String realpath;

    public Map<String,Printer> getPrintersInfo() throws IOException {
        //SNMP init and configure
        SnmpQuerier snmpquerier = new SnmpQuerier();
        ObjectMapper m = new ObjectMapper();

        Map<String, PrinterTemplate> ptempmap = new HashMap<>();    //temporary map
        Map<String, Printer> pmap = new HashMap<>();                //final map

        //get ip from ip.txt
        List<String> iplist = new ArrayList<>();
        Scanner in = new Scanner(new File(realpath +"/"+"ip.txt"));
        while (in.hasNextLine()) iplist.add(in.nextLine());

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
                e.printStackTrace();
            }
        }
        return pmap;
    }

}
