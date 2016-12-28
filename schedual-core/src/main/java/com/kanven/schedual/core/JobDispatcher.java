package com.kanven.schedual.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.register.ChildrenListener;
import com.kanven.schedual.register.Constants;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.transport.client.Client;
import com.kanven.schedual.transport.client.NettyClient;
import com.kanven.schedual.transport.client.pool.PoolConfig;

public class JobDispatcher implements ChildrenListener {

	private Register register;

	private PoolConfig config;

	private Clustor clustor;

	private boolean avaliable = false;

	private Lock lock = new ReentrantLock();

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
		return client;
	}

	private void init() {
		if (register == null) {
			throw new RuntimeException("注册中心没有初始化！");
		}
		if (clustor == null) {
			throw new RuntimeException("集群没有初始化！");
		}
		register.addChildrenListener(Constants.EXECUTOR_ROOT, this);
		avaliable = true;
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

	public void alloc(Job job) {
		if (!avaliable) {
			lock.lock();
			try {
				if (!avaliable) {
					init();
				}
			} finally {
				lock.unlock();
			}
		}
		clustor.alloc(job);
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public void setConfig(PoolConfig config) {
		this.config = config;
	}

	public void setClustor(Clustor clustor) {
		this.clustor = clustor;
	}

}
