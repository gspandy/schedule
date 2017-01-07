package com.kanven.schedual.transport.client.api;

import com.kanven.schedual.transport.client.command.Command;

public interface Sender<C> {

	/**
	 * 发送指令
	 * 
	 * @param command
	 * @throws Exception
	 */
	<T> T send(Command<C> command, Transform transform) throws Exception;

}
