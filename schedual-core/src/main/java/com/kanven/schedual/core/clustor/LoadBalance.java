package com.kanven.schedual.core.clustor;

import java.util.List;

import com.kanven.schedual.transport.client.api.Client;

public interface LoadBalance <C> {

	Client<C> select();

	void refresh(List<Client<C>> clients);

}
