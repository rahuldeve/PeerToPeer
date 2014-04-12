/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Other
 */
public class ServiceDiscovery implements Runnable{
    
    private String type = "_chat._tcp.local.";
    private JmDNS jmdns = null;
    private ServiceListener listener = null;
    private ServiceInfo serviceInfo;
    
    ContactResolve resolver;
    
    
    public ServiceDiscovery(){
        
        resolver = new ContactResolve();
        
        
        
        
        
        try {
            jmdns = JmDNS.create();
            
            jmdns.addServiceListener(type, listener = new ServiceListener() {
                
                @Override
                public void serviceResolved(ServiceEvent ev) {
                    
                    String additions = "";
                    if (ev.getInfo().getInetAddresses() != null && ev.getInfo().getInetAddresses().length > 0) {
                        additions = ev.getInfo().getInetAddresses()[0].getHostAddress();
                        System.out.println("Service resolved: " + ev.getInfo().getQualifiedName() + " port:" + ev.getInfo().getPort() + " "+additions);
                        
                        //maintain a record of previous ipaddr
                        //call only if new
                        
                        resolver.establishConnection(additions);
                    }
                }

                @Override
                public void serviceRemoved(ServiceEvent ev) {
                    //notifyUser("Service removed: " + ev.getName());
                    
                    //notify as offline
                    resolver.setOffline(ev.getInfo().getInetAddresses()[0].getHostAddress());
                    
                }

                @Override
                public void serviceAdded(ServiceEvent event) {
                    // Required to force serviceResolved to be called again (after the first search)
                    jmdns.requestServiceInfo(event.getType(), event.getName(), 1);
                }
            });
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ServiceDiscovery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    

    @Override
    public void run() {
        
        
       Thread senddetails = new Thread((Runnable) resolver);
        senddetails.start();
        
        
        ServiceDiscovery discoverer = new ServiceDiscovery();
        
        
    }
    
}
