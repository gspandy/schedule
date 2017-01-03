package com.kanven.schedual.transport.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel channel)
								throws Exception {
							channel.pipeline()
									.addLast(new LineBasedFrameDecoder(1024))
									.addLast(new StringDecoder())
									.addLast(new StringEncoder())
									.addLast(
											new ChannelInboundHandlerAdapter() {

												private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(
														20);

												{
													new StringQueue().start();
												}

												class StringQueue extends
														Thread {

													private int count = 1;

													@Override
													public void run() {
														while (true) {
															if (count <= 20) {
																try {
																	String content = "count"
																			+ count;
																	System.out
																			.println(content);
																	queue.put(content);
																} catch (InterruptedException e1) {
																	// TODO
																	// Auto-generated
																	// catch
																	// block
																	e1.printStackTrace();
																}
																if (count == 10) {
																	try {
																		Thread.sleep(5000L);
																	} catch (InterruptedException e) {
																		e.printStackTrace();
																	}
																}
																++count;
															} else {
																break;
															}
														}
													}

												}

												@Override
												public void channelActive(
														final ChannelHandlerContext ctx)
														throws Exception {
													while (true) {
														String str = queue
																.take();
														String content = str
																+ System.getProperty("line.separator");
														System.out
																.println("==="
																		+ content);
														ctx.writeAndFlush(content);
													}

												}

												@Override
												public void channelRead(
														ChannelHandlerContext ctx,
														Object msg)
														throws Exception {
													System.out.println("msg:"
															+ msg);
												}

												@Override
												public void channelReadComplete(
														ChannelHandlerContext ctx)
														throws Exception {
													super.channelReadComplete(ctx);
													ctx.flush();
												}

												@Override
												public void exceptionCaught(
														ChannelHandlerContext ctx,
														Throwable cause)
														throws Exception {
													super.exceptionCaught(ctx,
															cause);
													ctx.close();
												}

											});
						}
					});
			ChannelFuture future = bootstrap.connect("127.0.0.1", 8090).sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
