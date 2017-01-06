package com.kanven.schedual.core.clustor.impl;

import java.util.ArrayList;
import java.util.List;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.core.clustor.LoadBalance;
import com.kanven.schedual.transport.client.api.Client;
import com.kanven.schedual.transport.client.api.Transform;

public class DefaultClustorImpl<C> implements Clustor<C> {

	private LoadBalance<C> loadBalance;

	private List<Client<C>> clients;

	public <T> T send(C command, Transform<C> transform) throws Exception {
		Client<C> client = loadBalance.select();
		return client.send(command, transform);
	}

	public synchronized void refresh(List<Client<C>> clients) {
		List<Client<C>> origins = this.clients;
		this.clients = clients;
		loadBalance.refresh(clients);
		if (origins == null || origins.size() <= 0) {
			return;
		}
		List<Client<C>> abandons = new ArrayList<Client<C>>();
		for (Client<C> client : origins) {
			if (clients.contains(client)) {
				continue;
			}
			abandons.add(client);
		}
		destroy(abandons);
	}

	private void destroy(List<Client<C>> abandons) {
		Discard<C> discard = new Discard<C>(abandons);
		discard.start();
	}

	public void setLoadBalance(LoadBalance<C> loadBalance) {
		this.loadBalance = loadBalance;
	}

	private static class Discard<C> extends Thread {

		private List<Client<C>> abandons;

		public Discard(List<Client<C>> abandons) {
			this.abandons = abandons;
		}

		@Override
		public void run() {
			if (abandons != null && abandons.size() > 0) {
				for (Client<C> abandon : abandons) {
					if (!abandon.isClosed()) {
						abandon.close();
					}
				}
				abandons = null;
			}
		}

	}

}
