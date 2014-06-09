/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.util.HashMap;

/**
 *
 * @author Other
 */
public class MessageStorage {

    //store individual messages
    HashMap<String, String> history;

    public MessageStorage() {

        history = new HashMap<>();
    }

    public String getUserConversation(String user) {

        String conversation = history.get(user);
        return conversation;

    }

    public void storeUserConversation(String user, String conversation) {

        if (history.containsKey(user)) {
            history.put(user, conversation);

        } else {
            history.put(user, "");
        }
    }

}
