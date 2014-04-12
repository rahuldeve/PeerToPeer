/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Gui.GuiUpdater;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javax.swing.SwingWorker;

/**
 *
 * @author Other
 */

//netty server side handler
@Sharable
public class InputHandler extends ChannelInboundHandlerAdapter{
    
    public GuiUpdater updater;
    
    public InputHandler(GuiUpdater updater){
        this.updater = updater;
    }
    
    
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
        ByteBuf in  = (ByteBuf)msg;
        
        String message = in.toString(io.netty.util.CharsetUtil.US_ASCII);
        
        
        //convert message
        updater.updategui("jggf");
        //System.out.println(message);
        //wrute to gui
        

        
        
    }
    
    

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
    
}
