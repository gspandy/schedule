package com.kanven.schedual.core.server;

public final class ServerInfo {

	private final String ip;

	private final int port;

	public ServerInfo(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ServerInfo)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ServerInfo info = (ServerInfo) obj;
		if (this.port == info.port && this.ip.equals(info.ip)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "ServerInfo [ip=" + ip + ", port=" + port + "]";
	}

}
