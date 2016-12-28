package com.kanven.schedual.core.clustor;

import java.util.List;

import com.kanven.schedual.transport.client.Client;

public interface LoadBalance {

	Client select();

	void refresh(List<Client> clients);

}
