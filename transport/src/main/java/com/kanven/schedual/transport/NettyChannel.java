package com.kanven.schedual.transport;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.ResponseProto.Response;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class NettyChannel {

	private static final Logger log = LoggerFactory.getLogger(NettyChannel.class);

	private Channel channel;

	private String uuid;

	private NettyBootstrap bootstrap;

	{
		String uuid = UUID.randomUUID().toString();
		this.uuid = uuid.replaceAll("-", "");
	}

	NettyChannel(NettyBootstrap bootstrap) throws InterruptedException {
		this.bootstrap = bootstrap;
		open();
	}

	void open() throws InterruptedException {
		long start = System.currentTimeMillis();
		ChannelFuture future = bootstrap.connect();
		boolean completed = future.awaitUninterruptibly(bootstrap.getConnectTimeout(), TimeUnit.MILLISECONDS);
		boolean seccussed = future.isSuccess();
		if (completed && seccussed) {
			channel = future.channel();
			if (log.isDebugEnabled()) {
				long cost = System.currentTimeMillis() - start;
				log.debug(uuid + "连接建立成功，耗时：" + cost);

			}
			return;
		}
		future.cancel(true);
		Throwable t = future.cause();
		if (t != null) {
			throw new RuntimeException("连接建立出现异常!", t);
		} else {
			throw new RuntimeException("连接建立超时！");
		}
	}

	public Response request(Request request) {
		return request(request, Constants.DEFAULT_TIME_OUT);
	}

	public Response request(Request request, long timeout) {
		String requestId = request.getRequestId();
		NettyResponse response = new NettyResponse(requestId, timeout);
		bootstrap.regist(request.getRequestId(), response);
		ChannelFuture future = channel.writeAndFlush(request);
		boolean completed = future.awaitUninterruptibly(timeout, TimeUnit.MILLISECONDS);
		if (completed && future.isSuccess()) {
			try {
				return response.getValue();
			} catch (Exception e) {
				log.error("获取结果出现异常！", e);
				bootstrap.unregist(requestId);
				return null;
			}
		}
		// 发送请求超时
		future.cancel(true);
		response = bootstrap.unregist(requestId);
		if (response != null) {
			response.cancel();
		}
		throw new RuntimeException(requestId + "请求超时！");
	}

	public boolean isAvailable() {
		return channel.isActive() ? true : false;
	}

	public void close() {
		channel.close();
	}

	@Override
	public String toString() {
		return "NettyChannel [uuid=" + uuid + "]";
	}

}
