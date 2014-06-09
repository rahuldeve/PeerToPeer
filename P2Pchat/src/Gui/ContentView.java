/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Advertise.Contact;
import Advertise.ContactResolve;
import Advertise.ServiceDiscovery;
import Advertise.ServiceRegister;
import Communicate.InputServer;
import Communicate.Message;
import Communicate.OutputHandler;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author Other
 */
public class ContentView extends javax.swing.JPanel {

    /**
     * Creates new form ContentView
     */
    GuiUpdater updater;
    InputServer inputserver;
    ServiceDiscovery servicediscovery;
    ServiceRegister serviceregistery;
    ContactResolve resolver;

    List contacts;
    HashMap<String, Contact> contactmap = new HashMap<>();
    MessageStorage history;

    public static Contact self;

    public ContentView() {
        initComponents();
    }

    public static void setSelf(String uname, String ipaddr) {
        self = new Contact(ipaddr, uname);
    }

    public final void initInputServer() {

        updater = new GuiUpdater();
        inputserver = new InputServer(8080, updater);

        updater.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                String message = (String) evt.getNewValue();
                

                //intext.setText(intext.getText() + "\n" + message);
            }
        });

    }

    public final void initDiscovery() {

        updater = new GuiUpdater();

        servicediscovery = new ServiceDiscovery();
        serviceregistery = new ServiceRegister();
        resolver = new ContactResolve(servicediscovery, updater, self.getName());

        updater.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                contactmap = (HashMap) evt.getNewValue();
                HashMap temp = contactmap;
                List<Contact> contacts = new ArrayList(temp.values());

                contactlist.removeAll();

                Iterator<Contact> iter = contacts.iterator();
                DefaultListModel model = new DefaultListModel();

                while (iter.hasNext()) {

                    //System.out.println(iter.next());
                    //Contact temp =(Contact) iter.next();
                    model.addElement(iter.next().getName());
                    contactlist.setModel(model);

                }

            }

        });

    }

    public void sendMessage() {

        String temp = contactlist.getSelectedValue().toString();
        Contact w = contactmap.get(temp);

        String from = testgiu.self.getName();
        String to = w.getIp();
        String content = messageBox.getText();
        Message message = new Message(content, to, from);
        OutputHandler handler = new OutputHandler();
        handler.sendMessage(message);

    }

    public void startup() {

        Thread inputServerThread = new Thread(inputserver);
        Thread serviceRegisteryThread = new Thread(serviceregistery);
        Thread serviceDiscoverythread = new Thread(servicediscovery);
        Thread resolverThread = new Thread(resolver);

        inputServerThread.start();
        serviceRegisteryThread.start();
        serviceDiscoverythread.start();
        resolverThread.start();

    }

    public void shutdown() {

        inputserver.shutdown();
        serviceregistery.shutdown();
        servicediscovery.shutdown();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageBox = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        recivedFeild = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        contactlist = new javax.swing.JList();

        sendButton.setText("Send");

        recivedFeild.setColumns(20);
        recivedFeild.setRows(5);
        jScrollPane1.setViewportView(recivedFeild);

        contactlist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(contactlist);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageBox, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendButton)
                    .addComponent(messageBox, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList contactlist;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField messageBox;
    private javax.swing.JTextArea recivedFeild;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
