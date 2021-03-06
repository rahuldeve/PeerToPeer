/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Advertise;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 *
 * @author Other
 */
public class ServiceRegister implements Runnable{

    private JmDNS mdnsServer = null;

    public ServiceRegister() {
    }
    
    public void shutdown(){
        mdnsServer.unregisterAllServices();
        
    }

    @Override
    public void run() {

        try {

            mdnsServer = JmDNS.create(InetAddress.getLocalHost());
            ServiceInfo testService = ServiceInfo.create("_chat._tcp.local.", "Test Service", 6666, "test service");
            mdnsServer.registerService(testService);
            System.out.println("registered service as _workstation._tcp.local.");

        } catch (IOException ex) {
            Logger.getLogger(ServiceRegister.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
