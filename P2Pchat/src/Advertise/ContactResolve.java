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
import java.util.ArrayList;
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
    
    Contact self;
    List<Contact> contacts; //need a diff data structure
    
    Object lock;
    
    GuiUpdater updater;
    
    
    
    public ContactResolve(GuiUpdater updater){
        
        self = new Contact("asd", "192.168.1.2");   //get from gui
        List contacts = new ArrayList<Contact>();
        
        this.updater = updater;
        
    }

    ContactResolve() {
        
        self = new Contact("asd", "192.168.1.2");   //get from gui
        List contacts = new ArrayList<Contact>();
        
    }
    
    
    //TODO: try advertisments?
    public void resolveContact(Contact contact){    //updater method
  
        //checks if contact already exists
        System.out.println("inside func");
       //synchronized(lock)
       {
           System.out.println("enterind sync"+contact.ipaddr);
            Iterator<Contact> iter = contacts.iterator();
            boolean found;
            found = false;
            
           
            

            while(iter.hasNext() && found==false){

                Contact temp = iter.next();
                System.out.println(temp.name+"inside while");
                if(temp.getName().equals(contact.getName()))
                {
                    temp.ipaddr = contact.ipaddr;
                    temp.online = true;
                    found = true;
                    
                    //updare ui


                }

            }

            if(iter.hasNext()==false){
                contacts.add(contact);
                //update ui
                System.out.println("updating");
                
                
                
                
                
                
                //updater.updategui(contacts);
            }
            
            //lock.notify();
            
       }
        
        
        
        
        //yes? then set as online and change the ip
        //else adds new contact to list
    }
    
    public void establishConnection(String ipaddr) {    
        
        try {
            //socket connect
            Socket s = new Socket(ipaddr,6666);  
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
    
    
    
    public void sendDetails() throws IOException, JAXBException{
        
        ServerSocket ss = new ServerSocket(6666);
        
        Socket s = ss.accept();
        
        
        XStream xs = new XStream(new StaxDriver());
        ObjectOutputStream out = xs.createObjectOutputStream(s.getOutputStream());
        
        out.writeObject(this.self);
        
       
    }
    
    
    
    public void setOffline(String ipaddr){      //updater method
        // set recived contact status as offline
        // thread issues may happen here
        
        synchronized(lock)
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
            
            
           /* if(Thread.holdsLock(lock))
            {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
            
            lock.notify();
            
        }
        
        
    }
    
    
    
    /*public void sendContact(){
        
        //return contact details
        synchronized(lock)
        {
            
            try {
                
                
                //smething.updateContactList(contacts);
                //wake up updater thread in gui
                lock.wait();
                
                
                
                
                /* if(Thread.holdsLock(lock))
                {
                try {
                lock.wait();
                } catch (InterruptedException ex) {
                Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
    }*/

    
    
    
    @Override
    public void run() {
        
        try {
            
            sendDetails();
            
            
        } catch (IOException ex) {
            Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ContactResolve.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
