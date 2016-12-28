package com.kanven.schedual.core.clustor.loadbalance;

import java.util.List;

import com.kanven.schedual.core.clustor.LoadBalance;
import com.kanven.schedual.transport.client.Client;

public class RandomLoadBalance implements LoadBalance {

	private List<Client> clients;

	public Client select() {
		if (clients != null) {
			int size = clients.size();
			int r = (int) (Math.random() * size);
			for (int i = 0; i < size; i++) {
				int index = (i + r) % size;
				Client client = clients.get(index);
				if (!client.isClosed()) {
					return client;
				}
			}
		}
		throw new RuntimeException("没有可用的服务单元！");
	}

	public void refresh(List<Client> clients) {
		this.clients = clients;
	}

}
