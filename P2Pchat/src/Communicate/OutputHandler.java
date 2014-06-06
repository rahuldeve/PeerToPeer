/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communicate;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Other
 */
public class OutputHandler {

    //run it under a swing worker
    public void sendMessage(Message message) {

        try {

            //TODO: use netty for sending data and use an encoder for encoding the message
            
            Socket s = new Socket(message.to, 8080);
            
            //xml messages?
            XStream xs = new XStream(new StaxDriver());
            String xml = xs.toXML(message);
            
            OutputStream out = s.getOutputStream();
            out.write(xml.getBytes());
            out.close();
            s.close();

        } catch (IOException ex) {
            Logger.getLogger(OutputHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
