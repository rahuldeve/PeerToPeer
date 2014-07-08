/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communicate;

import Core.MessageNotifier;
import Gui.GuiUpdate;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 *
 * @author rahul dev e
 */
public class InputServerInitializer extends ChannelInitializer<SocketChannel> {
    
    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();
    

    private static InputServerHandler SERVER_HANDLER;
    
    public InputServerInitializer(MessageNotifier notifier){
        
        SERVER_HANDLER = new InputServerHandler(notifier);
        
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        
        ChannelPipeline pipeline = ch.pipeline();

        

        // Add the text line codec combination first,
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // the encoder and decoder are static as these are sharable
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        // and then business logic.
        pipeline.addLast(SERVER_HANDLER);
        
        
        
        
    }
    
}
