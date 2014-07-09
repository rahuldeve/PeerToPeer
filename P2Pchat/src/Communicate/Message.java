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
    public static int TYPE_LOGOFF = 3;
    public String content;
    public String to;
    public String from;
    public int msgType;
    
    
    public Message(String content, String to,String from,int msgType){
        this.content = content;
        this.to = to;
        this.from = from;
        this.msgType = msgType;
    }
        
}
