package com.kanven.schedual.core.server;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.register.Event;
import com.kanven.schedual.register.Listener;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.register.RegisterException;
import com.kanven.schedual.register.node.Node;
import com.kanven.schedual.transport.server.NettyServer;

/**
 * 服务抽象类
 * 
 * @author kanven
 *
 */
public abstract class AbstractServer extends Node implements Server, Listener {

	private static final Logger log = LoggerFactory.getLogger(AbstractServer.class);

	private String ip;

	private int port;

	private String root;

	private Register register;

	private NettyServer server;

	private boolean available = false;

	private Lock lock = new ReentrantLock();

	public void start() {
		lock.lock();
		try {
			check();
			if (log.isDebugEnabled()) {
				log.debug(this + "服务开始启动...");
			}
			try {
				String path = register.regist(this);
				this.setPath(path);
				register.addListener(this);
			} catch (RegisterException e) {
				log.error("服务(" + this + ")注册失败！　", e);
				return;
			}
			try {
				available = true;
				server = new NettyServer(port);
				server.registe(this);
				server.start();
				if (log.isDebugEnabled()) {
					log.debug(this + "服务完成启动...");
				}
			} catch (InterruptedException e) {
				available = false;
				log.error("服务(" + this + ")启动失败！", e);
			}
		} finally {
			lock.unlock();
		}
	}

	private void check() {
		if (StringUtils.isEmpty(ip)) {
			throw new RuntimeException("服务没有指定IP地址！");
		}
		if (port <= 0) {
			throw new RuntimeException("服务端口(" + port + ")不合法!");
		}
		if (register == null) {
			throw new RuntimeException("服务没有指定注册中心！");
		}
		if (StringUtils.isEmpty(root)) {
			throw new RuntimeException("服务没有指定注册根路径！");
		}
		if (server == null) {
			throw new RuntimeException("服务没有指定传输器！");
		}
		onCheck();
	}

	protected void onCheck() {

	}

	public boolean isAvailable() {
		lock.lock();
		try {
			return available;
		} finally {
			lock.unlock();
		}
	}

	public void close() {
		lock.lock();
		try {
			available = false;
			if (register != null) {
				try {
					register.unregist(this);
				} catch (RegisterException e) {
					log.error("服务(" + this + ")删除节点(" + getPath() + ")出现异常！", e);
				}
				register.removeListener(this);
			}
			if (server != null) {
				server.unregiste(this);
				server.stop();
			}
		} finally {
			lock.unlock();
		}
	}

	public void onDisconnected(Event event) {

	}

	public void onConnected(Event event) {

	}

	public void onExpired(Event event) {
		try {
			register.regist(this);
		} catch (RegisterException e) {
			log.error("服务重新注册失败！", e);
			lock.lock();
			try {
				available = false;
				close();
			} finally {
				lock.unlock();
			}
		}
	}

	public void onInited(Event event) {

	}

	@Override
	protected String buildData() {
		return "ip=" + ip + ";port=" + port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public NettyServer getServer() {
		return server;
	}

	public void setServer(NettyServer server) {
		this.server = server;
	}

	@Override
	public String toString() {
		return "AbstrancServer [ip=" + ip + ", port=" + port + ", root=" + root + ", available=" + available + "]";
	}

}
