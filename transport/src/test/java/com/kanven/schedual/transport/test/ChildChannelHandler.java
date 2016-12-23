package com.kanven.schedual.transport.test;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		 // 以下两行代码为了解决半包读问题  
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));  
        ch.pipeline().addLast(new StringDecoder());  
  
        ch.pipeline().addLast(new TimeServerHandler()); 
	}
	
}
