package com.kanven.schedual.register.node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 服务节点
 * 
 * @author kanven
 * 
 */
public abstract class Node {
	/**
	 * 根节点
	 */
	private String root;
	/**
	 * 节点创建时间
	 */
	private String createdTime;
	/**
	 * 节点全路径
	 */
	private String path;
	/**
	 * 节点健康情况
	 */
	protected boolean availabel = false;
	/**
	 * 是否是master节点
	 */
	private boolean master;

	{

		Date now = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		createdTime = format.format(now);
	}

	public final String getData() {
		String dt = "createdTime=" + createdTime;
		String data = buildData();
		if (StringUtils.isNotEmpty(dt)) {
			return data + ";" + dt;
		}
		return dt;
	}

	/**
	 * 构建节点数据，键值对间使用;分开
	 * 
	 * @return
	 */
	protected abstract String buildData();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		Node node = (Node) obj;
		if (node == this) {
			return true;
		}
		if (path.equals(node.path)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Node [root=" + root + ", createdTime=" + createdTime
				+ ", path=" + path + ", master=" + master + "]";
	}

}
