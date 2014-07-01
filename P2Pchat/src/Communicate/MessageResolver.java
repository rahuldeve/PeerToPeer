/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communicate;

/**
 *
 * @author rahul dev e
 */
public class MessageResolver {
    
    Message message;
    
    public MessageResolver(Message message){
        this.message = message;
        
    }
    
    public void resolveMessage(){
        
        if(message.content.isEmpty()){
            //resolve contact
        }else{
            //send to gui
        }
        
    }
    
}
