package com.kanven.schedual.exactor;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.RequestProto.Task;
import com.kanven.schedual.network.protoc.ResponseProto.Response;
import com.kanven.schedual.quartz.JobConfig;
import com.kanven.schedual.quartz.JobException;
import com.kanven.schedual.quartz.JobManager;
import com.kanven.schedual.register.Constants;
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

	private static final String DEFAULT_EXACTOR_ROOT = Constants.EXECUTOR_ROOT;

	private String ip;

	private int port = DEFAULT_EXACTOR_PORT;

	private Register register;

	private NettyServer server;

	private JobManager manager;

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
		if (register == null) {
			throw new RuntimeException("任务执行器没有指定注册中心！");
		}
		if (StringUtils.isEmpty(getRoot())) {
			setRoot(DEFAULT_EXACTOR_ROOT);
		}
	}

	public void init() throws RegisterException, JobException, InterruptedException {
		check();
		register.regist(this);
		manager = JobManager.getInstance();
		server = new NettyServer(port);
		server.registe(this);
		server.start();
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

	public Object receive(Object o) {
		if (o instanceof Request) {
			Request request = (Request) o;
			if (request.getType() == MessageType.TASK) {
				Response.Builder rb = Response.newBuilder();
				rb.setRequestId(request.getRequestId());

				Task task = request.getTask();
				JobConfig config = new JobConfig();
				config.setId(task.getId());
				config.setGroup(task.getGroup());
				config.setName(task.getName());
				config.setUrl(task.getUrl());
				config.setStartTime(new Date(task.getStartTime()));
				config.setCron(task.getCron());
				try {
					manager.add(config);
					rb.setStatus(200);
					rb.setMsg("添加任务成功！　");
				} catch (JobException e) {
					log.error("任务添加失败！", e);
					rb.setStatus(400);
					rb.setMsg(e.getMessage());
				}
				return rb.build();
			}
		}
		return null;
	}

	public void onDisconnected(Event event) {

	}

	public void onConnected(Event event) {

	}

	public void onExpired(Event event) {
		try {
			register.regist(this);
		} catch (RegisterException e) {
			log.error(this + "向注册中心注册失败！", e);
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
