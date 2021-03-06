/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communicate;

import Core.Message;
import Core.MessageNotifier;
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
public class InputServerHandler extends SimpleChannelInboundHandler<String> {
    
    MessageNotifier notifier;
    
    public InputServerHandler(MessageNotifier notifier){
        this.notifier = notifier;
        
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        
        
        // inititate handshake
        Message self = Core.Node.getSelf();
        
        XStream xs = new XStream(new StaxDriver());
        String xml  = xs.toXML(self);
        
        System.out.println("sending first handshake: "+xml);
        ctx.write(xml+"\r\n");
        ctx.flush();
        
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        
        if(msg.isEmpty()){
            
            //do nothing
            
        }else{
            
            XStream xs = new XStream(new StaxDriver());
            Message message = (Message)xs.fromXML(msg);
            
            if(message.msgType==Message.TYPE_LOGOFF){
                
                ctx.close();
                
            }
            
            notifier.notifyNewMessage(message);
            
        }
        
    }
   
}
