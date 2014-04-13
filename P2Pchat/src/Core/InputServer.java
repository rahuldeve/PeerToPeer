/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Gui.GuiUpdater;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Other
 */

//Activates the input netty server fot accepting incoming mmessages


public class InputServer implements Runnable{
    
    private int port;
    
    public InputHandler handler;
    
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;

    public InputServer(int port, GuiUpdater updater) {
        this.port = port;
        handler = new InputHandler(updater);
    }

    @Override
    public void run() {
        
        bossGroup = new NioEventLoopGroup(); 
        workerGroup = new NioEventLoopGroup();
        
        
        ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) 
             .childHandler(new ChannelInitializer<SocketChannel>() { 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(handler);
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          
             .childOption(ChannelOption.SO_KEEPALIVE, true); 
            
            
            ChannelFuture f; 
            
            
        try {
            
            f = b.bind(port).sync(); 
            f.channel().closeFuture().sync();
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(InputServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        finally {
            
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            
        }

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            
        
        
    }
    
    public void shutdown(){
        
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
    
}
