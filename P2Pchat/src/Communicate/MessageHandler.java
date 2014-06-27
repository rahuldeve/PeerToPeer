/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communicate;

import Gui.GuiUpdater;

/**
 *
 * @author rahul dev e
 */
public class MessageHandler {
    
    Message message;
    GuiUpdater updater;
    
    public MessageHandler(GuiUpdater updater){
        this.updater = updater;
    }
    
    public void resolveMessage(Message message){
        
        if(message.msgType==Message.TYPE_CONTACT){
            //resolve contact
        }else{
            //send to gui
           updater.updategui(message);
        }
        
    }
    
}
