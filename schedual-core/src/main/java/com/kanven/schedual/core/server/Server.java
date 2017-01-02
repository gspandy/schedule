package com.kanven.schedual.core.server;

import com.kanven.schedual.register.RegisterException;
import com.kanven.schedual.transport.server.MessageReceiver;

/**
 * 服务接口
 * 
 * @author kanven
 *
 */
public interface Server extends MessageReceiver {

	void start() throws RegisterException, InterruptedException;

	boolean isAvailable();

	void close() throws RegisterException;

}
