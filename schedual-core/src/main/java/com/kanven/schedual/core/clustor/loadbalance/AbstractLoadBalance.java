package com.kanven.schedual.core.clustor.loadbalance;

import java.util.List;

import com.kanven.schedual.core.clustor.LoadBalance;
import com.kanven.schedual.transport.client.api.Client;

public abstract class AbstractLoadBalance<C> implements LoadBalance<C> {

	private List<Client<C>> clients;

	public Client<C> select() {
		if (clients != null) {
			Client<C> client = onSelect();
			if (client != null) {
				return client;
			}
		}
		throw new RuntimeException("没有可用的服务单元！");
	}

	protected abstract Client<C> onSelect();

	public void refresh(List<Client<C>> clients) {
		this.clients = clients;
	}

	List<Client<C>> getClients() {
		return clients;
	}

}
