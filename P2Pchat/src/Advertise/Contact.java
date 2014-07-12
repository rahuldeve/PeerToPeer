/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Advertise;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Other
 */
@XmlRootElement
public class Contact {

    String name;
    String ipaddr;
    boolean online;

    public Contact(String name, String ipaddr) {

        this.name = name;
        this.ipaddr = ipaddr;
        this.online = true;

    }

    @XmlAttribute
    public String getName() {
        return this.name;

    }

    @XmlElement
    public String getIp() {
        return this.ipaddr;
    }

    @XmlElement
    public boolean getOnlineStatus() {
        return this.online;
    }

}
