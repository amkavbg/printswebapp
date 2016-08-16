package engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

public class SnmpQuerier {

    private static final Logger log = LoggerFactory.getLogger(SnmpQuerier.class);
    private String SNMP_COMMUNITY = "public";
    private int SNMP_RETRIES = 3;
    private final long SNMP_TIMEOUT = 2000L;
    private Snmp snmp = null;
    private TransportMapping transport = null;
    protected volatile boolean started = false;

    public String send (String ip, String oid) throws IOException {
        if (!started) {
            log.debug("Service SNMP not started");
            throw new IllegalStateException("Not started");
        }
        // create the target
        Target target = getTarget("udp:"+ip+"/161");
        // create the PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);
        // send the PDU
        ResponseEvent event = snmp.send(pdu, target, null);
        if (event.getResponse() != null) {
            Variable var = event.getResponse().get(0).getVariable();
            if (var instanceof OctetString){
                OctetString octetStr = (OctetString) var;
                byte[] bytes = octetStr.getValue();
                return new String(bytes,"UTF-8");
            }
            return event.getResponse().get(0).toValueString();
            } else {
            log.debug("Timeout exceeded for  "+ip+".");
            return "null";
        }
    }

    public void start() throws IOException {
        if (started) {
            return;
        }
        transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
        started = true;
    }

    public void stop() throws IOException {
        started = false;
        try {
            if (transport != null) {
                transport.close();
                transport = null;
            }
        } finally {
            if (snmp != null) {
                snmp.close();
                snmp = null;
            }
        }

    }

    private Target getTarget(String address) {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(SNMP_COMMUNITY));
        target.setAddress(targetAddress);
        target.setRetries(SNMP_RETRIES);
        target.setTimeout(SNMP_TIMEOUT);
        target.setVersion(SnmpConstants.version1);
        return target;
    }

}
