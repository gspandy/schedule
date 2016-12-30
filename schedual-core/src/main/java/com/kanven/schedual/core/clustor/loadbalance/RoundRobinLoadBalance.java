package com.kanven.schedual.core.clustor.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.kanven.schedual.transport.client.api.Client;

public class RoundRobinLoadBalance<C> extends AbstractLoadBalance<C> {

	private AtomicInteger counter = new AtomicInteger(0);

	@Override
	protected Client<C> onSelect() {
		List<Client<C>> clients = getClients();
		for (int i = 0, len = clients.size(); i < len; i++) {
			int index = (statistic() + i) % len;
			Client<C> client = clients.get(index);
			if (!client.isClosed()) {
				return client;
			}
		}
		return null;
	}

	private int statistic() {
		return counter.incrementAndGet() & Integer.MAX_VALUE;
	}

}
