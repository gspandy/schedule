package com.kanven.schedual.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.core.clustor.impl.DefaultClustorImpl;
import com.kanven.schedual.register.ChildrenListener;
import com.kanven.schedual.register.Constants;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.transport.client.Client;
import com.kanven.schedual.transport.client.NettyClient;

public class JobDispatcher implements ChildrenListener {

	private Register register;

	private ExactorConfig config;

	private Clustor clustor = new DefaultClustorImpl();

	private Client createClient(String dt) {
		String[] items = dt.split(";");
		String ip = "";
		int port = 0;
		for (String item : items) {
			if (item.startsWith("ip=")) {
				ip = item.replace("ip=", "");
				continue;
			}
			if (item.startsWith("port=")) {
				port = Integer.parseInt(item.replace("port=", ""));
				continue;
			}
		}
		if (StringUtils.isEmpty(ip) || port <= 0) {
			return null;
		}
		NettyClient client = new NettyClient(ip, port);
		client.setMaxIdle(config.getMaxIdle());
		return client;
	}

	public void init() {
		register.addChildrenListener(Constants.EXECUTOR_ROOT, this);
	}

	public void alloc(Job job) {
		clustor.alloc(job);
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public void setConfig(ExactorConfig config) {
		this.config = config;
	}

	public void handChildrenChange(List<String> children) {
		List<Client> clients = new ArrayList<Client>();
		for (String child : children) {
			Client client = createClient(child);
			if (client != null) {
				clients.add(client);
			}
		}
		clustor.refresh(clients);
	}

}
