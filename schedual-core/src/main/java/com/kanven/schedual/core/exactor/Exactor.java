package com.kanven.schedual.core.exactor;

import com.kanven.schedual.core.NodeInfo;
import com.kanven.schedual.register.Event;
import com.kanven.schedual.register.Listener;
import com.kanven.schedual.register.node.Node;

public class Exactor extends Node implements Listener {

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

	public void onDisconnected(Event event) {
		
	}

	public void onConnected(Event event) {
		
	}

	public void onExpired(Event event) {
		
	}

	public void onInited(Event event) {
		
	}

}
