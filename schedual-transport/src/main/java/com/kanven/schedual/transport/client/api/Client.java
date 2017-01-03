package com.kanven.schedual.transport.client.api;

import com.kanven.schedual.transport.client.PoolConfig;

public interface Client<C> extends Sender<C> {

	void setConfig(PoolConfig config);

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
