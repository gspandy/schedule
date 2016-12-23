package com.kanven.schedual.transport;

public interface Client {

	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws Exception
	 */
	NettyChannel getChannel() throws Exception;

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