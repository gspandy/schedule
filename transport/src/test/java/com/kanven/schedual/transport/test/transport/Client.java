package com.kanven.schedual.transport.test.transport;

import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.transport.client.NettyClient;

public class Client {

	public static void main(String[] args) throws Exception {
		final NettyClient client  = new NettyClient("127.0.0.1",8090);
		client.setMinIdle(5);
		for (int i = 0; i < 2; i++) {
			final int n = i;
			new Thread() {
				@Override
				public void run() {
					for (int j = 0; j < 1; j++) {
						int seq = n*100 + j ;
						Request.Builder builder = Request.newBuilder();
						builder.setType(MessageType.TASK);
						builder.setRequestId(seq + "");
						try {
							System.out.println(client.getChannel().request(builder.build(),-1));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					System.out.println("==="+n);
				}

			}.start();
		}

		// client.close();
	}

}
