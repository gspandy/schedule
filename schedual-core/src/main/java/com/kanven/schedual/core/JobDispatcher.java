package com.kanven.schedual.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.core.clustor.impl.DefaultClustorImpl;
import com.kanven.schedual.register.Constants;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.register.RegisterException;
import com.kanven.schedual.transport.client.Client;
import com.kanven.schedual.transport.client.NettyClient;

public class JobDispatcher {

	private Register register;

	private ExactorConfig config;

	private Clustor clustor = new DefaultClustorImpl();

	private Watcher childrenWatcher = new Watcher() {

		public void process(WatchedEvent event) {
			EventType type = event.getType();
			if (EventType.NodeChildrenChanged == type) {
				init();
			}
		}

	};

	private List<String> getChildren() {
		List<String> dts = new ArrayList<String>();
		try {
			List<String> children = register.getChildren(Constants.EXECUTOR_ROOT, childrenWatcher);
			for (String path : children) {
				String dt = register.getData(path);
				if (StringUtils.isEmpty(dt)) {
					continue;
				}
				dts.add(dt);
			}
		} catch (RegisterException e) {

		}
		return dts;
	}

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
		List<Client> clients = new ArrayList<Client>();
		List<String> children = getChildren();
		for (String child : children) {
			Client client = createClient(child);
			if (client != null) {
				clients.add(client);
			}
		}
		clustor.refresh(clients);
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

}
