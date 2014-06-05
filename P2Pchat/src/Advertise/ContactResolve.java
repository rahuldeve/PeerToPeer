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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Other
 */
public class ContactResolve extends Thread {

    //CONSUMER
    Contact self;
    GuiUpdater updater;
    ServiceDiscovery producer;
    HashMap<String, Contact> contacts = new HashMap<>();

    public ContactResolve(ServiceDiscovery producer) {

        self = new Contact("asd", "192.168.1.2");   //get from gui
        this.producer = producer;

    }

    public ContactResolve(ServiceDiscovery producer, GuiUpdater updater) {

        self = new Contact("asd", "192.168.1.2");   //get from gui
        this.producer = producer;
        this.updater = updater;

    }

    //TODO: try advertisments?
    public void resolveContact(Contact contact) {    //updater method

        //checks if contact already exists
        System.out.println(Thread.currentThread().getId());

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
        // set recived contact status as offline
        // thread issues may happen here

        /* synchronized(lock)
         {
            
           
            
         Iterator<Contact> iter = contacts.iterator();
         Contact temp;



         while(iter.hasNext()){

         temp = iter.next();
         if(temp.getIp().equals(ipaddr)){
         temp.online = false;
         //update ui
         //updater.updategui(contacts);
         }
         }
            
            
         if(Thread.holdsLock(lock))
         {
         try {
         lock.wait();
         } catch (InterruptedException ex) {
         Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
            
         lock.notify();
            
         }
        
         */
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

                System.out.println(mess);
                establishConnection(mess);

            }

            try {
                sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
