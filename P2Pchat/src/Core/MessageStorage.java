/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.HashMap;
import javax.swing.DefaultListModel;

/**
 *
 * @author rahul dev e
 */
public class MessageStorage {

    public HashMap<String, String> history;
    DefaultListModel<String> userList;

    public MessageStorage() {
        history = new HashMap<>();
        userList = new DefaultListModel<>();
    }

    public String getUserConversation(String username) {

        String conversation = history.get(username);
        return conversation;

    }

    public void storeUserConversation(String username, String conversation) {
        String previous = history.get(username);
        String current = previous+conversation+"\n";
        history.put(username, current);
    }
    
    public DefaultListModel  addUser(String username){
        history.put(username, "");
        userList.addElement(username);
        return userList;
    }
    
    public DefaultListModel setOffline(String username){
        
        userList.removeElement(username);
        return userList;
        
    }
    
    public DefaultListModel getUserList(){
        return userList;
    }
    
    

}
