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
    
    public String content;
    public String to;
    public String from;
    
    public Message(String msg, String to,String from){
        this.content = msg;
        this.to = to;
        this.from = from;
    }
    
}
