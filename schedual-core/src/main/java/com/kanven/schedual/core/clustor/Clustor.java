package com.kanven.schedual.core.clustor;

import java.util.List;

import com.kanven.schedual.core.Job;
import com.kanven.schedual.transport.client.Client;


public interface Clustor {

	void refresh(List<Client> clients);

	void setLoadBalance(LoadBalance loadBalance);
	
	void alloc(Job job);

}
