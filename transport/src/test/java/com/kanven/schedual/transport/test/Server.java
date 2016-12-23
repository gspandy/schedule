package com.kanven.schedual.transport.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup work = new NioEventLoopGroup();
		EventLoopGroup boss = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boss, work);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel channel)
						throws Exception {
					channel.pipeline().addLast(new LineBasedFrameDecoder(1024))
							.addLast(new StringDecoder())
							.addLast(new StringEncoder())
							.addLast(new ChannelInboundHandlerAdapter() {

								@Override
								public void channelRead(
										ChannelHandlerContext ctx, Object msg)
										throws Exception {
									System.out.println("msg:" + msg);
									ctx.writeAndFlush("I received......"
											+ System.getProperty("line.separator"));
								}

								@Override
								public void channelReadComplete(
										ChannelHandlerContext ctx)
										throws Exception {
									ctx.flush();
								}

								@Override
								public void exceptionCaught(
										ChannelHandlerContext ctx,
										Throwable cause) throws Exception {
									super.exceptionCaught(ctx, cause);
									ctx.close();
								}

							});
				}
			});
			ChannelFuture future = bootstrap.bind(8090).sync();
			future.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}

	}
}
