package com.kanven.schedual.transport.client;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.network.protoc.ResponseProto.Response;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;

final class NettyBootstrap {

	private static final Logger log = LoggerFactory.getLogger(NettyBootstrap.class);

	private Bootstrap bootstrap = new Bootstrap();

	private EventLoopGroup group = null;

	private long connectTimeout;

	private int threads;

	private String ip;

	private int port;

	private ConcurrentMap<String, NettyResponse> ress = new ConcurrentHashMap<String, NettyResponse>();

	public NettyBootstrap(String ip, int port) {
		this(ip, port, -1);
	}

	public NettyBootstrap(String ip, int port, int threads) {
		this(ip, port, threads, 3000);
	}

	public NettyBootstrap(String ip, int port, int threads, long connectTimeout) {
		this.ip = ip;
		this.port = port;
		this.threads = threads;
		this.connectTimeout = connectTimeout;
		initialBootstrap();
	}

	private void initialBootstrap() {
		if (log.isDebugEnabled()) {
			log.debug("开始初始化netty...");
		}
		if (group == null) {
			group = threads <= 0 ? new NioEventLoopGroup() : new NioEventLoopGroup(threads);
		}
		bootstrap.group(group);
		bootstrap.option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();
				// 添加心跳检查
				pipeline.addLast(new IdleStateHandler(10, 10, 15, TimeUnit.SECONDS));
				pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast(new ProtobufDecoder(Response.getDefaultInstance()));
				pipeline.addLast(new LengthFieldPrepender(4));
				pipeline.addLast(new ProtobufEncoder());
				pipeline.addLast(new NettyChannelHandler(NettyBootstrap.this));
			}
		});
		if (log.isDebugEnabled()) {
			log.debug("完成初始化netty...");
		}
	}

	ChannelFuture connect() {
		return bootstrap.connect(ip, port);
	}

	public void regist(String key, NettyResponse response) {
		if (response != null) {
			if (!ress.containsKey(key)) {
				ress.put(key, response);
			}
		}
	}

	public NettyResponse unregist(String key) {
		return ress.remove(key);
	}

	public void close() {
		if (group != null) {
			group.shutdownGracefully();
		}
		if (ress.size() > 0) {
			Set<String> keys = ress.keySet();
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				NettyResponse response = ress.get(key);
				response.cancel();
				iterator.remove();
			}
		}
	}

	public long getConnectTimeout() {
		return connectTimeout;
	}

	public int getThreads() {
		return threads;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

}
