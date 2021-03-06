/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communicate;

import Core.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author rahul dev e
 */
@Sharable
public class OutputServerHandler extends SimpleChannelInboundHandler<String> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        
        XStream xs = new XStream(new StaxDriver());
        Message message = (Message) xs.fromXML(msg);
        
        if(message.msgType==Message.TYPE_CONTACT){
            
            //write back self
            Message self = Core.Node.getSelf();
            String xml = xs.toXML(self);
            
            System.out.println("sending second handshake: "+ xml);
            ctx.writeAndFlush(xml+"\r\n");
            
        }
        
        
    }
    
}
