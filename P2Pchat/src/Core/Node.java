/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Advertise.ServiceDiscovery;
import Advertise.ServiceRegister;
import Communicate.InputServer;
import Communicate.OutputServer;
import Communicate.ServiceNotifier;
import Gui.GuiUpdater;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rahul dev e
 */
public class Node {
    
    MessageStorage storage;
    IPMapper ipMapper;
    
    ServiceNotifier notifier;
    MessageNotifier messageNotifier;
    GuiUpdater updater;
    
    InputServer inputserver;
    OutputServer outputserver;
    
    ServiceDiscovery servicediscovery;
    ServiceRegister serviceregister;
    
    static Message self = new Message("asd", null, "localhost", Message.TYPE_CONTACT);
    
    public Node(GuiUpdater updater){
        
        storage = new MessageStorage();
        ipMapper = new IPMapper();
        
        notifier = new ServiceNotifier();
        messageNotifier = new MessageNotifier();
        this.updater = updater;
        
        servicediscovery = new ServiceDiscovery(notifier);
        serviceregister = new ServiceRegister();
        
        inputserver = new InputServer(messageNotifier);
        outputserver = new OutputServer(notifier);
        
        
    }
    
    public MessageStorage getStorage(){
        return this.storage;
    }
    
    public static  void setSelf(String username){
        
        String selfip = null;
        try {
            selfip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        self = new Message(username,null, selfip, Message.TYPE_CONTACT);
        
    }
    
    public static synchronized Message getSelf(){
        return self;
}
    
    public void init(){
        
        messageNotifier.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                
                Message message =(Message) evt.getNewValue();
                
                
                if(message.msgType==Message.TYPE_CONTACT){
                    
                    //message storage
                    Object temp = storage.addUser(message.content);
                    
                    //create ipmapper entry
                    ipMapper.storeUsertoIPMapping(message.content,message.from);
                    
                    //update ui
                    updater.updateGui(Gui.test.PROP_CONTACT,temp);
                    
                }else if(message.msgType==Message.TYPE_MESSAGE){
                    
                    //store it in message storege
                    storage.storeUserConversation(message.from, message.content);
                    
                    //update ui
                    updater.updateGui(Gui.test.PROP_MESSAGE,message);
                    
                }else if (message.msgType==Message.TYPE_LOGOFF){
                    
                    //logoutsignal
                    
                    Object temp = storage.setOffline(message.content);
                    updater.updateGui(Gui.test.PROP_CONTACT,temp);
                    
                }else{
                    
                }
                
                
            }
        });
        
        Thread serviceDiscoveryThread = new Thread(servicediscovery);
        Thread serviceRegistryThread = new Thread(serviceregister);
        
        serviceRegistryThread.start();
        serviceDiscoveryThread.start();
        
        Thread inputServerThread = new Thread(inputserver);
        Thread outputServerThread = new Thread(outputserver);
        
        inputServerThread.start();
        outputServerThread.start();
        
        
    }
    
    public void sendMessage(String username,String text){
        
        String to = ipMapper.getUserToIPMapping(username);
        Message message = new Message(text, username, self.content, Message.TYPE_MESSAGE);
        outputserver.sendMessage(to, message);
        
    }
    
    
    
    public void logoff(){
        
        servicediscovery.shutdown();
        serviceregister.shutdown();
        
        inputserver.shutdown();
        outputserver.shutdown();
      
    }
     
}
