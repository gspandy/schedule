package com.kanven.schedual.core.server;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.register.Event;
import com.kanven.schedual.register.Listener;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.register.RegisterException;
import com.kanven.schedual.register.node.Node;

/**
 * 中央服务器
 * 
 * @author kanven
 * 
 */
public final class Server extends Node implements Listener {

	private static final Logger log = LoggerFactory.getLogger(Server.class);

	private static final int DEFAULT_PORT = 7980;

	private final String ip;

	private final Integer port;

	private Register register;

	public Server(String ip) {
		this(ip, DEFAULT_PORT);
	}

	public Server(String ip, Integer port) {
		if (StringUtils.isEmpty(ip)) {
			throw new IllegalArgumentException("IP地址不能空！");
		}
		ip = ip.trim();
		if (ip == null) {
			throw new IllegalArgumentException("IP地址不能空！");
		}
		this.ip = ip;
		this.port = port;
	}

	public void init() throws RegisterException {
		if (register == null) {
			throw new RuntimeException("没有设置注册中心");
		}
		if (log.isDebugEnabled()) {
			log.debug("服务信息：" + this);
		}
		register.addListener(this);
		registServer();
	}

	private void registServer() throws RegisterException {
		String path = register.regist(this);
		register.exists(path, serverWatcher);
		monitor();
	}

	private void monitor() {
		List<String> paths;
		try {
			paths = register.getChildren(getRoot(), null);
			int index = paths.indexOf(getPath());
			if (index < 0) {
				// todo
				return;
			}
			if (index == 0) {
				setMaster(true);
				// todo
				return;
			}
			// 监听当前服务前一个节点状态
			String pre = paths.get(index - 1);
			register.exists(pre, preWatcher);
		} catch (RegisterException e) {
			log.error("节点监控出现异常！", e);
			// 是否需要做对应的处理
		}
	}

	private Watcher preWatcher = new Watcher() {

		public void process(WatchedEvent event) {
			EventType type = event.getType();
			if (EventType.NodeDeleted == type) {
				monitor();
			}
		}

	};

	private Watcher serverWatcher = new Watcher() {

		public void process(WatchedEvent event) {
			EventType type = event.getType();
			// 节点被意外删除
			if (EventType.NodeDeleted == type) {
				// todo
				// 如果是master服务取消对executor服务的变更监听
				if (isMaster()) {
					// todo 任务执行服务
				}
			}
		}

	};

	public void onDisconnected(Event event) {

	}

	public void onConnected(Event event) {

	}

	public void onExpired(Event event) {

	}

	public void onInited(Event event) {
		// 重新初始化服务
		try {
			registServer();
		} catch (RegisterException e) {
			log.error("重新初始化服务出现异常！", e);
		}
	}

	@Override
	protected String buildData() {
		return "ip=" + ip + ";port=" + port;
	}

	@Override
	public String toString() {
		return "Server [ip=" + ip + ", port=" + port + "]";
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

}
