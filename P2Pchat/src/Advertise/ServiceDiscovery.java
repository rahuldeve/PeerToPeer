/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Advertise;

import Communicate.ServiceNotifier;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

/**
 *
 * @author Other
 */
public class ServiceDiscovery implements Runnable {

    private final String type = "_chat._tcp.local.";
    private JmDNS jmdns = null;
    private ServiceListener listener = null;
    private ServiceInfo serviceInfo;
    
    String additions;//use queue later
    ServiceNotifier notifier;
    

    public ServiceDiscovery(ServiceNotifier notifier) {
        this.notifier = notifier;
    }

    
    public void shutdown(){
        jmdns.removeServiceListener(type, listener);
    }

    @Override
    public void run() {

        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
            jmdns.addServiceListener(type, listener = new ServiceListener() {

                @Override
                public void serviceResolved(ServiceEvent ev) {

                    String additions = "";
                    if (ev.getInfo().getInetAddresses() != null && ev.getInfo().getInetAddresses().length > 0) {
                        additions = ev.getInfo().getInetAddresses()[0].getHostAddress();
                        System.out.println("Service resolved: " + ev.getInfo().getQualifiedName() + " port:" + ev.getInfo().getPort() + " " + additions);
                        notifier.setSampleProperty(additions);
                    }
                }

                @Override
                public void serviceRemoved(ServiceEvent ev) {
                    
                    //System.out.println("Service removed: " + ev.getName());
                    //String ipaddr = ev.getInfo().getInetAddresses()[0].getHostAddress();
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

}
