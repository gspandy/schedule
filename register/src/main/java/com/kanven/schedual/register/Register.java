package com.kanven.schedual.register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.register.node.Node;

/**
 * 注册中心
 * 
 * @author kanven
 * 
 */
public class Register {

	private static final Logger log = LoggerFactory.getLogger(Register.class);

	public static final int DEFAULT_SESION_TIMEOUT = 5000;

	private String conn;

	private int sessionTimeout = DEFAULT_SESION_TIMEOUT;

	private volatile boolean available = false;

	private ZooKeeper zk;

	private Dispatcher dispatcher = new RegisterDispatcher();

	private final Lock lock = new ReentrantLock();

	private Event createEvent(EventType type) {
		return new Event(type);
	}

	private Map<String, Set<ChildrenListener>> childrenListeners = new ConcurrentHashMap<String, Set<ChildrenListener>>();

	private List<String> getChildrenData(String path) {
		List<String> dts = new ArrayList<String>();
		try {
			List<String> paths = zk.getChildren(path, true);
			if (paths != null) {
				for (String p : paths) {
					try {
						p = path + "/" + p;
						dts.add(new String(zk.getData(p, false, null)));
					} catch (KeeperException e) {
						log.error("获取孩子节点（" + path + "）数据失败！", e);
					} catch (InterruptedException e) {
						log.error("获取孩子节点（" + path + "）数据失败！", e);
					}
				}
			}
		} catch (KeeperException e) {
			log.error("获取节点（" + path + "）孩子节点失败！", e);
		} catch (InterruptedException e) {
			log.error("获取节点（" + path + "）孩子节点失败！", e);
		}
		return dts;
	}

	private Watcher watcher = new Watcher() {

		public void process(WatchedEvent event) {
			org.apache.zookeeper.Watcher.Event.EventType type = event.getType();
			if (type == org.apache.zookeeper.Watcher.Event.EventType.NodeChildrenChanged) {
				String path = event.getPath();

				Set<ChildrenListener> listeners = childrenListeners.get(path);
				if (listeners != null) {
					for (ChildrenListener listener : listeners) {
						listener.handChildrenChange(getChildrenData(path));
					}
				}
				return;
			}
			KeeperState state = event.getState();
			if (state == KeeperState.Disconnected) { // 网络断开
				available = false;
				dispatcher.dispatch(createEvent(EventType.DISCONNECTED));
				return;
			}
			if (state == KeeperState.SyncConnected) {
				if (!available) { // 网络重连
					dispatcher.dispatch(createEvent(EventType.CONNECTED));
				}
				return;
			}
			if (state == KeeperState.Expired) { // 会话超时
				available = false;
				dispatcher.dispatch(createEvent(EventType.EXPIRED));
				init();
				return;
			}
		}

	};

	public Register(String conn, int sessionTimeout) {
		if (StringUtils.isEmpty(conn)) {
			throw new IllegalArgumentException("注册中心连接地址不能为空！");
		}
		this.conn = conn;
		this.sessionTimeout = sessionTimeout;
		init();
	}

	private void init() {
		try {
			lock.lock();
			zk = new ZooKeeper(this.conn, this.sessionTimeout, this.watcher);
			available = true;
		} catch (IOException e) {
			log.error("注册中心初始化失败！", e);
			available = false;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 节点注册
	 * 
	 * @param node
	 *            节点
	 * @throws RegisterException
	 */
	public String regist(Node node) throws RegisterException {
		if (!available) {
			throw new RuntimeException("注册中心不可用！");
		}
		if (node == null) {
			throw new IllegalArgumentException("节点为空！");
		}
		try {
			lock.lock();
			String dt = node.getData();
			if (StringUtils.isEmpty(dt)) {
				dt = "";
			}
			try {
				String root = createParentNode(node.getRoot());
				String path = zk.create(root, dt.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				node.setPath(path);
				return path;
			} catch (KeeperException e) {
				throw new RegisterException("节点注册失败！", e);
			} catch (InterruptedException e) {
				throw new RegisterException("节点注册失败！", e);
			}
		} finally {
			lock.unlock();
		}
	}

	public void addChildrenListener(String path, ChildrenListener childrenListener) {
		if (StringUtils.isNotEmpty(path) && childrenListener != null) {
			lock.lock();
			try {
				if (!childrenListeners.containsKey(path)) {
					childrenListeners.put(path, new HashSet<ChildrenListener>());
				}
				Set<ChildrenListener> listeners = childrenListeners.get(path);
				listeners.add(childrenListener);
				childrenListener.handChildrenChange(getChildrenData(path));
			} finally {
				lock.unlock();
			}
		}

	}

	/**
	 * 取消注册节点
	 * 
	 * @param node
	 *            节点
	 * @throws RegisterException
	 */
	public void unregist(Node node) throws RegisterException {
		if (node != null && available) {
			try {
				zk.delete(node.getPath(), -1);
			} catch (InterruptedException e) {
				throw new RegisterException(node.getData() + "节点移除失败！", e);
			} catch (KeeperException e) {
				throw new RegisterException(node.getData() + "节点移除失败！", e);
			}
		}
	}

	public List<String> getChildren(String path, Watcher watcher) throws RegisterException {
		if (StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("path参数不能为空！");
		}
		try {
			if (watcher == null) {
				return zk.getChildren(path, false);
			}
			return zk.getChildren(path, watcher);
		} catch (KeeperException e) {
			throw new RegisterException("获取" + path + "孩子节点信息失败！ ", e);
		} catch (InterruptedException e) {
			throw new RegisterException("获取" + path + "信息失败！ ", e);
		}
	}

	public Stat exists(String path, Watcher watcher) throws RegisterException {
		try {
			if (StringUtils.isEmpty(path)) {
				throw new IllegalArgumentException("path不能为空！");
			}
			if (watcher == null) {
				return zk.exists(path, false);
			}
			return zk.exists(path, watcher);
		} catch (InterruptedException e) {
			throw new RegisterException("获取" + path + "信息失败！ ", e);
		} catch (KeeperException e) {
			throw new RegisterException("获取" + path + "信息失败！ ", e);
		}
	}

	public String getData(String path) throws RegisterException {
		if (StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("path不能为空！");
		}
		try {
			return new String(zk.getData(path, false, new Stat()));
		} catch (KeeperException e) {
			throw new RegisterException("获取" + path + "节点数据失败！ ", e);
		} catch (InterruptedException e) {
			throw new RegisterException("获取" + path + "节点数据失败！", e);
		}
	}

	private String createParentNode(String root) throws RegisterException {
		if (StringUtils.isEmpty(root)) {
			throw new IllegalArgumentException("路径不能为空！");
		}
		if (null == root.trim()) {
			throw new IllegalArgumentException("路径不能为空！");
		}
		root = root.replace("\\", "/");
		if (root.startsWith("/")) {
			root = root.substring(1);
		}
		if (root.endsWith("/")) {
			root = root.substring(0, root.length() - 1);
		}
		String[] segs = root.split("/");
		StringBuilder sb = new StringBuilder("/");
		for (String seg : segs) {
			sb.append(seg);
			String p = sb.toString();
			try {
				if (zk.exists(p, false) == null) {
					zk.create(p, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
			} catch (KeeperException e) {
				throw new RegisterException("创建根节点出现异常！", e);
			} catch (InterruptedException e) {
				throw new RegisterException("创建根节点出现异常！", e);
			}
			sb.append("/");
		}
		return sb.toString();
	}

	public void addListener(Listener listener) {
		dispatcher.addListener(listener);
	}

	public void removeListener(Listener listener) {
		dispatcher.remove(listener);
	}

}
