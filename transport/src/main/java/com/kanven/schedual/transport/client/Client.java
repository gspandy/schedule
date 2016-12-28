package com.kanven.schedual.transport.client;

public interface Client {

	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws Exception
	 */
	NettyChannel getChannel() throws Exception;
	
	void returnChannel(NettyChannel nettyChannel);

	/**
	 * 服务是否关闭
	 * 
	 * @return
	 */
	boolean isClosed();

	/**
	 * 关闭服务
	 */
	void close();

}
