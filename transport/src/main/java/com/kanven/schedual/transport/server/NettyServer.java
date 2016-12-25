package com.kanven.schedual.transport.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.ResponseProto.Response;
import com.kanven.schedual.network.protoc.ResponseProto.Response.Builder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class NettyServer {

	private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

	private ServerBootstrap bootstrap = new ServerBootstrap();

	private EventLoopGroup bossGroup = new NioEventLoopGroup();

	private List<MessageReceiver> receivers = new ArrayList<MessageReceiver>();

	private EventLoopGroup workGroup;

	private int port;

	private int works = -1;

	public NettyServer() {

	}

	public NettyServer(int port) throws InterruptedException {
		this.port = port;
	}

	public void registe(MessageReceiver receiver) {
		if (!receivers.contains(receiver)) {
			receivers.add(receiver);
		}
	}

	public void unregiste(MessageReceiver receiver) {
		receivers.remove(receiver);
	}

	public void start() throws InterruptedException {
		init();
		ChannelFuture future = bootstrap.bind(port).sync();
		future.channel().closeFuture().sync();
	}

	public void stop() {
		bossGroup.shutdownGracefully();
		if (workGroup != null && !workGroup.isShutdown()) {
			workGroup.shutdownGracefully();
		}
		Iterator<MessageReceiver> iterator = receivers.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
	}

	private void init() throws InterruptedException {
		if (log.isDebugEnabled()) {
			log.debug("开始初始化服务，端口为：" + port);
		}
		workGroup = works > 0 ? new NioEventLoopGroup(works) : new NioEventLoopGroup();
		bootstrap.group(bossGroup, workGroup);
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.TCP_NODELAY, true)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();
				pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast(new ProtobufDecoder(Request.getDefaultInstance()));
				pipeline.addLast(new LengthFieldPrepender(4));
				pipeline.addLast(new ProtobufEncoder());
				pipeline.addLast(new ChannelInboundHandlerAdapter() {

					@Override
					public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
						Request request = (Request) msg;
						MessageType type = request.getType();
						if (type == MessageType.PING) {
							Builder builder = Response.newBuilder();
							builder.setStatus(200);
							builder.setType(MessageType.PONG);
							ctx.writeAndFlush(builder.build());
						} else {
							for (MessageReceiver receiver : receivers) {
								Object o = receiver.receive(msg);
								if (o != null) {
									ctx.writeAndFlush(msg);
								}
							}
						}
					}

					@Override
					public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
						log.error("handler处理出现异常！", cause);
						ctx.close();
					}

				});
			}
		});
		if (log.isDebugEnabled()) {
			log.debug("初始化完成...");
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getWorks() {
		return works;
	}

	public void setWorks(int works) {
		this.works = works;
	}

}
