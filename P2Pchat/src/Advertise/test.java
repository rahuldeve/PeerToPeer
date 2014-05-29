/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Advertise;

import Advertise.ServiceDiscovery;
import Advertise.ServiceRegister;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Other
 */
public class test {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       
        
        ServiceDiscovery servicediscoverythread = new ServiceDiscovery();
        ServiceRegister serviceregisterthread = new ServiceRegister();
        ContactResolve resolver  =new ContactResolve(servicediscoverythread);
        
       serviceregisterthread.start();
       servicediscoverythread.start();
       
        try {
            
            sleep(5000);
            resolver.start();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        // TODO code application logic here
    }
    
}
