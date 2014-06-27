/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communicate;

import Gui.GuiUpdater;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author Other
 */
//netty server side handler
@Sharable
public class InputHandler extends ChannelInboundHandlerAdapter {

    GuiUpdater updater;
    MessageHandler handler;

    public InputHandler(GuiUpdater updater) {
        
        this.updater = updater;
        handler = new MessageHandler(updater);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        
        ByteBuf in = (ByteBuf) msg;
        String xml = in.toString(io.netty.util.CharsetUtil.US_ASCII);
        
        XStream xs = new XStream(new StaxDriver());
        Message message = (Message) xs.fromXML(xml);

        //send to gui
        updater.updategui(message);
        
        //new additiion
        handler.resolveMessage(message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
