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
import Communicate.ServiceNotifier;
import Gui.GuiUpdate;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author rahul dev e
 */
public class Node {
    
    MessageStorage storage;
    
    ServiceNotifier notifier;
    MessageNotifier messageNotifier;
    GuiUpdate updater;
    
    InputServer inputserver;
    OutputServer outputserver;
    
    ServiceDiscovery servicediscovery;
    ServiceRegister serviceregister;
    
    static Message self = new Message("asd", null, "localhost", Message.TYPE_CONTACT);
    
    public Node(GuiUpdate updater){
        
        storage = new MessageStorage();
        
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
    
    public static void setSelf(String username){
        
        self = new Message(username,null, "localhost", Message.TYPE_CONTACT);
        
    }
    
    public static Message getSelf(){
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
                    
                    //update ui
                    updater.updateGui(temp);
                    
                }else if(message.msgType==Message.TYPE_MESSAGE){
                    
                    //store it in message storege
                    storage.storeUserConversation(message.from, message.content);
                }else if (message.msgType==Message.TYPE_LOGOFF){
                    
                    //logoutsignal
                    
                    Object temp = storage.setOffline(message.content);
                    updater.updateGui(temp);
                    
                }else{
                    
                }
                
                
            }
        });
        
        Thread serviceDiscoveryThread = new Thread(servicediscovery);
        Thread serviceRegistryThread = new Thread(serviceregister);
        
        serviceRegistryThread.start();
        serviceDiscoveryThread.start();
        
        inputserver.start();
        outputserver.start();
        
        
    }
    
    
    
    public void shutdown(){
        
        servicediscovery.shutdown();
        serviceregister.shutdown();
        
        inputserver.shutdown();
        outputserver.shutdown();
        
    }
     
}
