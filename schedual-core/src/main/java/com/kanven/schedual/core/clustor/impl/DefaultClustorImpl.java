package com.kanven.schedual.core.clustor.impl;

import java.util.List;

import com.kanven.schedual.command.Command;
import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.core.clustor.LoadBalance;
import com.kanven.schedual.transport.client.api.Client;
import com.kanven.schedual.transport.client.api.Transform;

public class DefaultClustorImpl<C> implements Clustor<C> {

	private LoadBalance<C> loadBalance;

	public <T> T send(Command<C> command, Transform<C> transform) throws Exception {
		Client<C> client = loadBalance.select();
		return client.send(command, transform);
	}

	public void refresh(List<Client<C>> clients) {
		// todo 无效客户端连接回收
		loadBalance.refresh(clients);
	}

	public void setLoadBalance(LoadBalance<C> loadBalance) {
		this.loadBalance = loadBalance;
	}

}
