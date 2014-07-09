/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communicate;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rahul dev e
 */
public class OutputServer extends Thread {
    
    static final int PORT = 8080;
    
    EventLoopGroup group;
    Bootstrap b;
    HashMap<String, Channel> channelMap;
    
    ServiceNotifier notifier;
    
    public OutputServer(ServiceNotifier notifier){
        
        this.notifier = notifier;
    }
    
    @Override
    public void run(){
        
       group = new NioEventLoopGroup();
       
       b = new Bootstrap();
       b.group(group)
         .channel(NioSocketChannel.class)
         .handler(new OutputServerInitializer());
       
       notifier.addPropertyChangeListener(new PropertyChangeListener() {

           @Override
           public void propertyChange(PropertyChangeEvent evt) {
               
               String ipaddr = (String) evt.getNewValue();
               initChannel(ipaddr);
               
           }
       });
       
    }
    
    public void initChannel(String ipaddr){
        
        try {
            
            System.out.println("attempting to connect :"+ipaddr);
            Channel ch = b.connect(ipaddr, PORT).sync().channel();
            channelMap.putIfAbsent(ipaddr, ch);
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(OutputServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void sendMessage(String ipaddr,Message message){
        
        Channel ch = channelMap.get(ipaddr);
        
        XStream xs = new XStream(new StaxDriver());
        String xml = xs.toXML(message);
        
        ch.writeAndFlush(xml+"\r\n");
        
    }
    
    public void shutdown(){
        
        //get each channels
        Channel ch = null;
        
        //send shutdownsignal
        Message logoutmessage = new Message(Core.Node.getSelf().content, null, Core.Node.getSelf().from, Message.TYPE_LOGOFF);
        XStream xs = new XStream(new StaxDriver());
        String xml = xs.toXML(logoutmessage);
        
        ch.writeAndFlush(xml+"\r\n");
        
        //ch.closeFtutre.sync
        ch.close();
        
        
        group.shutdownGracefully();
    }
    
}
