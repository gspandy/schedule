package com.kanven.schedual.transport.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {

	public void connect(int port, String host) {
		// 配置客户端的NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							// 以下两行代码为了解决半包读问题
							ch.pipeline().addLast(
									new LineBasedFrameDecoder(1024));
							ch.pipeline().addLast(new StringDecoder());

							ch.pipeline().addLast(new TimeClientHandler());
						}
					});
			// 发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();

			// 等待链路关闭
			f.channel().closeFuture().sync();

		} catch (Exception e) {
		} finally {
			// 退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		int port = 8080;
		if (null != args && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (Exception e) {
			}
		}
		new TimeClient().connect(port, "127.0.0.1");
	}

}
