/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Advertise.ServiceDiscovery;
import Advertise.ServiceRegister;
import Communicate.InputServer;
import Communicate.Message;
import Communicate.OutputServer;

/**
 *
 * @author rahul dev e
 */
public class Node {
    
    MessageStorage storage;
    InputServer inputserver;
    OutputServer outputserver;
    
    ServiceDiscovery servicediscovery;
    ServiceRegister serviceregister;
    
    public static Message self;
    
    
    public static void setSelf(String name,String ipaddr){
        
        
        
    }
    
}
