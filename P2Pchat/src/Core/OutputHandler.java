/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

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
    public void sendMessage(String message) {

        try {

            Socket s = new Socket("localhost", 8080);
            OutputStream out = s.getOutputStream();
            out.write(message.getBytes());
            out.close();
            s.close();

        } catch (IOException ex) {
            Logger.getLogger(OutputHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
