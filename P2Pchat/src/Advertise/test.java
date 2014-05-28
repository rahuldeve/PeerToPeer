/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Advertise;

import Advertise.ServiceRegister;
import Advertise.ServiceDiscovery;

/**
 *
 * @author Other
 */
public class test {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ContactResolve resolver  =new ContactResolve();
        Thread servicediscoverthread = new Thread(new ServiceDiscovery(resolver));
        Thread serviceregistrythread = new Thread(new ServiceRegister());
        
       serviceregistrythread.start();
       servicediscoverthread.start();
       
        // TODO code application logic here
    }
    
}
