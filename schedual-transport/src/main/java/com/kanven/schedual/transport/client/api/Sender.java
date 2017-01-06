package com.kanven.schedual.transport.client.api;

public interface Sender<C> {

	/**
	 * 发送指令
	 * 
	 * @param command
	 * @throws Exception
	 */
	<T> T send(C command, Transform<C> transform) throws Exception;

}
