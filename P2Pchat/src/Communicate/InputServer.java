/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Communicate;

import Core.MessageNotifier;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rahul dev e
 */
public class InputServer implements Runnable{
    
    static final int PORT = 8080;
    
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    
    MessageNotifier notifier;
    
    public InputServer(MessageNotifier notifier){
        this.notifier = notifier;
    }
    
    @Override
    public void run(){
        
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             //.handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new InputServerInitializer(notifier));

            b.bind(PORT).sync().channel().closeFuture().sync();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(InputServer.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
       
    }
    
    public void shutdown(){
        
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        
    }
    
}
