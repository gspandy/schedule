package com.kanven.schedual.core.exactor;

import com.kanven.schedual.core.NodeInfo;
import com.kanven.schedual.register.node.Node;

public class Exactor extends Node {

	private String ip;

	private int port;

	public Exactor(NodeInfo node) {
		this.ip = node.getIp();
		this.port = node.getPort();
		this.setRoot(node.getRoot());
		this.setPath(node.getPath());
	}

	@Override
	protected String buildData() {
		return "ip=" + ip + ";port=" + port;
	}

}
