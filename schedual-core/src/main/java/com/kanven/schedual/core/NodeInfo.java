package com.kanven.schedual.core;

import java.util.Date;

/**
 * 节点信息
 * 
 * @author kanven
 *
 */
public final class NodeInfo {

	private final String ip;

	private final int port;

	private final String root;

	private final String path;

	private final Date createTime;

	public NodeInfo(String ip, int port, String root, String path, Date createTime) {
		this.ip = ip;
		this.port = port;
		this.root = root;
		this.path = path;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "NodeInfo [ip=" + ip + ", port=" + port + ", root=" + root + ", path=" + path + ", createTime="
				+ createTime + "]";
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getRoot() {
		return root;
	}

	public String getPath() {
		return path;
	}

	public Date getCreateTime() {
		return createTime;
	}

}
