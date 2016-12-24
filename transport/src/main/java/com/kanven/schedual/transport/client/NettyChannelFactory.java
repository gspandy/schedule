package com.kanven.schedual.transport.client;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NettyChannelFactory implements PooledObjectFactory<NettyChannel> {

	private static final Logger log = LoggerFactory.getLogger(NettyChannelFactory.class);

	private NettyBootstrap bootstrap;

	public NettyChannelFactory(NettyBootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public PooledObject<NettyChannel> makeObject() throws Exception {
		NettyChannel channel = new NettyChannel(bootstrap);
		if (log.isDebugEnabled()) {
			log.debug("创建连接池对象：" + channel);
		}
		return new DefaultPooledObject<NettyChannel>(channel);
	}

	public void destroyObject(PooledObject<NettyChannel> p) throws Exception {
		NettyChannel channel = p.getObject();
		if (channel == null) {
			return;
		}
		channel.close();
		if (log.isDebugEnabled()) {
			log.debug("销毁连接池对象：" + channel);
		}
	}

	public boolean validateObject(PooledObject<NettyChannel> p) {
		NettyChannel channel = p.getObject();
		if (log.isDebugEnabled()) {
			log.debug("校验连接池对象：" + channel);
		}
		return channel.isAvailable();
	}

	public void activateObject(PooledObject<NettyChannel> p) throws Exception {
		NettyChannel channel = p.getObject();
		if (!channel.isAvailable()) {
			channel.open();
		}
	}

	public void passivateObject(PooledObject<NettyChannel> p) throws Exception {

	}

}