/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.HashMap;

/**
 *
 * @author rahul dev e
 */
public class MessageStorage {

    HashMap<String, String> history;

    public MessageStorage() {
        history = new HashMap<>();
    }

    public String getUserCnversation(String username) {

        String conversation = history.get(username);
        return conversation;

    }

    public void storeUserConversation(String usernme, String conversation) {
        history.putIfAbsent(usernme, conversation);
    }
    
    public void  addUser(String username){
        history.put(username, "");
    }

}
