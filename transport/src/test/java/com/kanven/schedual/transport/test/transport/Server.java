package com.kanven.schedual.transport.test.transport;

import com.kanven.schedual.transport.server.NettyServer;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		new NettyServer(8090);
	}

}
