package com.kanven.schedual.exactor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.register.Event;
import com.kanven.schedual.register.Listener;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.register.RegisterException;
import com.kanven.schedual.register.node.Node;
import com.kanven.schedual.transport.server.MessageReceiver;
import com.kanven.schedual.transport.server.NettyServer;

/**
 * 任务执行器
 * 
 * @author kanven
 *
 */
public class TaskExactor extends Node implements Listener, MessageReceiver {

	private static final Logger log = LoggerFactory.getLogger(TaskExactor.class);

	public static final int DEFAULT_EXACTOR_PORT = 7990;

	private String ip;

	private int port = DEFAULT_EXACTOR_PORT;

	private Register register;

	private NettyServer server;

	public TaskExactor() {

	}

	public TaskExactor(String ip, Register register) {
		this(ip, DEFAULT_EXACTOR_PORT, register);
	}

	public TaskExactor(String ip, int port, Register register) {
		this.ip = ip;
		this.port = port;
		this.register = register;
	}

	private void check() {
		if (StringUtils.isEmpty(ip)) {
			throw new RuntimeException("ip地址为空！");
		}
		if (StringUtils.isEmpty(getRoot())) {
			throw new RuntimeException("任务执行器根路径不能为空！ ");
		}
		if (register == null) {
			throw new RuntimeException("任务执行器没有指定注册中心！");
		}
	}

	public void init() {
		check();
		try {
			register.regist(this);
			server = new NettyServer(port);
			server.registe(this);
			server.start();
		} catch (RegisterException e) {
			log.error(this + "注册失败！　", e);
		} catch (InterruptedException e) {
			log.error("任务执行服务启动失败！", e);
		}
	}

	public void close() {
		if (register != null) {
			try {
				register.unregist(this);
			} catch (RegisterException e) {
				log.error("向注册中心注销失败！", e);
			}
		}
		if (server != null) {
			server.unregiste(this);
			server.stop();
		}
	}

	public void receive(Object o) {

	}

	public void onDisconnected(Event event) {

	}

	public void onConnected(Event event) {

	}

	public void onExpired(Event event) {
		init();
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

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	@Override
	public String toString() {
		return "TaskExactor [ip=" + ip + ", port=" + port + "]";
	}

}
