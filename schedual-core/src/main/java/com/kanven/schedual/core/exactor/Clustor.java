package com.kanven.schedual.core.exactor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.core.NodeInfo;
import com.kanven.schedual.register.Constants;
import com.kanven.schedual.register.Event;
import com.kanven.schedual.register.Listener;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.register.RegisterException;

/**
 * 任务执行服务
 * 
 * @author kanven
 *
 */
public class Clustor implements Listener {

	private static final Logger log = LoggerFactory.getLogger(Clustor.class);

	private String exactorRoot = Constants.EXECUTOR_ROOT;

	private Register register;

	public Clustor() {

	}

	public Clustor(Register register) {
		this(register, Constants.EXECUTOR_ROOT);
	}

	public Clustor(Register register, String exactorRoot) {
		this.exactorRoot = exactorRoot;
		check();
	}

	private void check() {
		if (register == null) {
			throw new IllegalArgumentException("注册中心不能为空！");
		}
		if (StringUtils.isEmpty(exactorRoot)) {
			throw new IllegalArgumentException("任务执行器根路径不能为空！");
		}
	}

	public void start() {
		check();
		initExactors();
	}

	public void stop() {

	}

	/**
	 * 初始化任务执行服务
	 */
	private void initExactors() {
		List<NodeInfo> infos = getExactors();
		for (NodeInfo info : infos) {

		}
	}

	/**
	 * 获取任务执行服务列表
	 * 
	 * @return
	 */
	private List<NodeInfo> getExactors() {
		List<NodeInfo> infos = new ArrayList<NodeInfo>();
		List<String> paths = null;
		try {
			paths = register.getChildren(exactorRoot, childrenWatcher);
		} catch (RegisterException e) {
			log.error("获取任务执行服务信息出现异常！", e);
		}
		if (paths != null) {
			for (String path : paths) {
				try {
					String dt = register.getData(path);
					if (StringUtils.isEmpty(dt)) {
						continue;
					}
					NodeInfo info = parse(dt, path);
					if (info != null) {
						infos.add(info);
					}
				} catch (RegisterException e) {
					log.error("获取节点（" + path + "）数据失败！", e);
				}
			}
		}
		return infos;
	}

	/**
	 * 节点信息解析
	 * 
	 * @param dt
	 * @param path
	 * @return
	 */
	private NodeInfo parse(String dt, String path) {
		if (StringUtils.isEmpty(dt)) {
			return null;
		}
		String[] items = dt.split(";");
		if (items == null || items.length <= 1) {
			log.error(dt + "数据不合法");
			return null;
		}
		String ip = "";
		int port = -1;
		Date createTime = null;
		try {
			for (String item : items) {
				if (item.startsWith("ip=")) {
					ip = item.replace("ip=", "");
					if (StringUtils.isEmpty(ip)) {
						log.error("解析数据（" + dt + "）获取ip地址失败！");
						return null;
					}
					continue;
				}
				if (item.startsWith("port=")) {
					String p = item.replace("port=", "");
					if (StringUtils.isEmpty(p)) {
						log.error("解析数据（" + dt + "）获取端口信息失败！");
						return null;
					}
					port = Integer.parseInt(p);
					continue;
				}
				if (item.startsWith("createTime=")) {
					String times = item.replace("createTime=", "");
					if (StringUtils.isNotEmpty(times)) {
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
						createTime = format.parse(times);
					}
					continue;
				}
			}
		} catch (Exception e) {
			log.error("节点数据（" + dt + "）解析出现异常！", e);
			return null;
		}
		return new NodeInfo(ip, port, exactorRoot, path, createTime);
	}

	private Watcher childrenWatcher = new Watcher() {

		public void process(WatchedEvent event) {
			EventType type = event.getType();
			if (EventType.NodeChildrenChanged == type) {
				initExactors();
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

	}

	public void setExactorRoot(String exactorRoot) {
		this.exactorRoot = exactorRoot;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

}
