package com.kanven.schedual.core.clustor.loadbalance;

import java.util.List;

import com.kanven.schedual.transport.client.api.Client;

public class RandomLoadBalance<C> extends AbstractLoadBalance<C> {

	@Override
	protected Client<C> onSelect() {
		List<Client<C>> clients = getClients();
		int size = clients.size();
		int r = (int) (Math.random() * size);
		for (int i = 0; i < size; i++) {
			int index = (i + r) % size;
			Client<C> client = clients.get(index);
			if (!client.isClosed()) {
				return client;
			}
		}
		return null;
	}

}