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
public class IPMapper {
    
    HashMap<String,String> mapping;
    
    public IPMapper(){
        mapping = new HashMap<>();
    }
    
    public void storeUsertoIPMapping(String username,String ipaddr){
        mapping.put(username, ipaddr);
    }
    
    public String getUserToIPMapping(String username){
        return mapping.get(username);
    }
    
    
    
}
    

