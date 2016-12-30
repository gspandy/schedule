package com.kanven.schedual.core.clustor.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.core.clustor.loadbalance.RandomLoadBalance;
import com.kanven.schedual.core.config.ProtocolConfig;
import com.kanven.schedual.register.ChildrenListener;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.transport.client.PoolConfig;
import com.kanven.schedual.transport.client.api.Client;
import com.kanven.schedual.transport.client.netty.NettyClient;

public class ClustorFactory<C> implements ChildrenListener {

	private static final Logger log = LoggerFactory.getLogger(ClustorFactory.class);

	private Register register;

	private ProtocolConfig config;

	private String parent;

	private Clustor<C> clustor;

	private boolean available = false;

	public Clustor<C> getClustor() {
		if (!available) {
			init();
		}
		return clustor;
	}

	private void init() {
		if (register == null) {
			throw new RuntimeException("注册中心没有初始化！");
		}
		if (StringUtils.isEmpty(parent)) {
			throw new RuntimeException("没有指定父节点！");
		}
		this.clustor = new DefaultClustorImpl<C>();
		this.clustor.setLoadBalance(new RandomLoadBalance<C>());
		register.addChildrenListener(this.parent, this);
	}

	public void handChildrenChange(List<String> children) {
		List<Client<C>> clients = new ArrayList<Client<C>>();
		for (String child : children) {
			Client<C> client = createClient(child);
			if (client != null) {
				clients.add(client);
			}
		}
		clustor.refresh(clients);
	}

	private Client<C> createClient(String dt) {
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
		PoolConfig poolConfig = new PoolConfig();
		try {
			BeanUtils.copyProperties(poolConfig, this.config);
		} catch (IllegalAccessException e) {
			log.error("参数复制失败！", e);
		} catch (InvocationTargetException e) {
			log.error("参数复制失败！", e);
		}
		poolConfig.setIp(ip);
		poolConfig.setPort(port);
		return new NettyClient<C>(poolConfig);
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public void setConfig(ProtocolConfig config) {
		this.config = config;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
