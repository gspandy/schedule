package com.kanven.schedual.transport;

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

	private int port;

	public NettyServer(int port) throws InterruptedException {
		this.port = port;
		init();
	}

	private void init() throws InterruptedException {
		if (log.isDebugEnabled()) {
			log.debug("开始初始化服务，端口为：" + port);
		}
		EventLoopGroup workGroup = new NioEventLoopGroup();
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
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
							Builder builder = Response.newBuilder();
							switch (type) {
							case TASK:
								if (log.isDebugEnabled()) {
									log.debug("消息内容：" + request);
								}
								builder.setStatus(200);
								builder.setMsg("成功");
								builder.setRequestId(request.getRequestId());
								Response response = builder.build();
								ctx.writeAndFlush(response);
								break;
							case PING:
								builder.setStatus(200);
								builder.setType(MessageType.PONG);
								ctx.writeAndFlush(builder.build());
								break;
							default:
								break;
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
				log.debug("服务拉起...");
			}
			ChannelFuture future = bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

}
