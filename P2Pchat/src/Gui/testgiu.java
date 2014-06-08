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
public class testgiu extends javax.swing.JFrame {

    GuiUpdater updater;
    InputServer inputserver;
    ServiceDiscovery servicediscoverythread;
    ServiceRegister serviceregisterthread;
    ContactResolve resolver;

    List contacts;
    HashMap<String, Contact> contactmap = new HashMap<>();

    public static Contact self;

    /**
     * Creates new form testgiu
     */
    public testgiu() {

        initComponents();
        initDiscovery();
        initInputServer();
        setSelf();

    }

    public static void setSelf() {
        self = new Contact("asd", "192.168.1.2");
    }

    public void initInputServer() {

        updater = new GuiUpdater();
        inputserver = new InputServer(8080, updater);

        updater.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                String message = (String) evt.getNewValue();
                intext.setText(intext.getText() + "\n" + message);

            }
        });

    }

    public void initDiscovery() {

        updater = new GuiUpdater();

        servicediscoverythread = new ServiceDiscovery();
        serviceregisterthread = new ServiceRegister();
        resolver = new ContactResolve(servicediscoverythread, updater,self.getName());

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sendtext = new javax.swing.JTextField();
        Switch = new javax.swing.JToggleButton();
        sendbutton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        intext = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        contactlist = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Switch.setText("On/Off");
        Switch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SwitchMouseClicked(evt);
            }
        });

        sendbutton.setText("Send");
        sendbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendbuttonMouseClicked(evt);
            }
        });

        intext.setColumns(20);
        intext.setRows(5);
        jScrollPane1.setViewportView(intext);

        contactlist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Loading" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(contactlist);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(sendtext)
                        .addGap(18, 18, 18)
                        .addComponent(sendbutton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addComponent(Switch)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendtext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendbutton)
                    .addComponent(Switch))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendbuttonMouseClicked

        String temp = contactlist.getSelectedValue().toString();
        Contact w = contactmap.get(temp);

        String from = testgiu.self.getName();
        String to = w.getIp();
        String content = sendtext.getText();
        Message message = new Message(content, to, from);
        OutputHandler handler = new OutputHandler();
        handler.sendMessage(message);


    }//GEN-LAST:event_sendbuttonMouseClicked

    private void SwitchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SwitchMouseClicked

        if (Switch.isSelected()) {

            Switch.setText("ON");
            Thread inputServerThread = new Thread(inputserver);
            inputServerThread.start();

            serviceregisterthread.start();
            servicediscoverythread.start();
            resolver.start();

        } else {
            Switch.setText("OFF");
            
            inputserver.shutdown();
            serviceregisterthread.shutdown();
            servicediscoverythread.shutdown();
        }


    }//GEN-LAST:event_SwitchMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(testgiu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new testgiu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Switch;
    private javax.swing.JList contactlist;
    private javax.swing.JTextArea intext;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton sendbutton;
    private javax.swing.JTextField sendtext;
    // End of variables declaration//GEN-END:variables
}
