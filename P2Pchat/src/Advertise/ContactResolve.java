/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Advertise;

import Gui.GuiUpdater;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Other
 */
public class ContactResolve implements Runnable {

    //CONSUMER
    Contact self;
    GuiUpdater updater;
    ServiceDiscovery producer;
    HashMap<String, Contact> contacts = new HashMap<>();

    public ContactResolve(ServiceDiscovery producer) {

        self = new Contact("asd", "192.168.1.2");   //get from gui
        this.producer = producer;

    }

    public ContactResolve(ServiceDiscovery producer, GuiUpdater updater,String username) {
        
        String selfip = null;
        
        try {
            selfip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
        }

        self = new Contact(username, selfip);   //get from gui
        this.producer = producer;
        this.updater = updater;

    }

    //TODO: try advertisments?
    public void resolveContact(Contact contact) {    //updater method
        
        if (contacts.containsKey(contact.getName())) {

            //set online
            updater.updategui(contacts);

        } else {

            contacts.put(contact.getName(), contact);
            updater.updategui(contacts);
        }

        System.out.println("inside func");

        //yes? then set as online and change the ip
        //else adds new contact to list
    }

    public void establishConnection(String ipaddr) {

        try {
            //socket connect
            Socket s = new Socket(ipaddr, 6666);
            System.out.println("established" + ipaddr);
            //keep 6666 as port for resolving?

            XStream xs = new XStream(new StaxDriver());
            ObjectInputStream in = xs.createObjectInputStream(s.getInputStream());

            Contact contact = (Contact) in.readObject();
            resolveContact(contact);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendDetails() throws IOException, JAXBException {

        ServerSocket ss = new ServerSocket(6666);

        while (true) {
            Socket s = ss.accept();

            XStream xs = new XStream(new StaxDriver());
            ObjectOutputStream out = xs.createObjectOutputStream(s.getOutputStream());

            out.writeObject(this.self);
            out.close();

        }

    }

    public void setOffline(String ipaddr) {      //updater method
        
        
        List<Contact> temp = new ArrayList<>(contacts.values());
        Iterator<Contact> iter = temp.iterator();
        
        while(iter.hasNext()){
            
            Contact tempc = iter.next();
            if(ipaddr.equals(tempc.getIp())){
                contacts.remove(tempc.getName());
            }
            
            updater.updategui(contacts);
            
        }
        
    }

    @Override
    public void run() {

        Thread sendDetailsthread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {

                        sendDetails();

                    } catch (IOException | JAXBException ex) {
                        Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        sendDetailsthread.start();

        while (true) {

            String mess = producer.getEvent();
            if (mess != null) {
                
                //1=establish connetcion
                //0=set offline
                
                if(mess.startsWith("1")){
                    
                    System.out.println(mess);
                    establishConnection(mess.substring(1));
                    
                }else{
                    
                    setOffline(mess.substring(1));
                    
                }

                
                

            }

            try {
                sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
