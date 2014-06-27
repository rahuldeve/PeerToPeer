/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communicate;

/**
 *
 * @author Other
 */
public class Message {
    
    //diff approach
    //use blank messages to establish initial contact
    //use netty to handle all input and output
    public static int TYPE_CONTACT  = 1;
    public static int TYPE_MESSAGE = 2;
    public String content;
    public String to;
    public String from;
    public int msgType;
    
    public Message(String msg, String to,String from){
        this.content = msg;
        this.to = to;
        this.from = from;
    }
    
    public Message(String msg, String to,String from, int msgType){
        this.content = msg;
        this.to = to;
        this.from = from;
        this.msgType = msgType;
    }
      
}
