package com.kanven.schedual.core.clustor;

import java.util.List;

import com.kanven.schedual.transport.client.api.Sender;
import com.kanven.schedual.transport.client.api.Client;

public interface Clustor<C> extends Sender<C> {

	void refresh(List<Client<C>> clients);

	void setLoadBalance(LoadBalance<C> loadBalance);

}
