package com.kanven.schedual.register;

public interface Listener {

	void onDisconnected(Event event);

	void onConnected(Event event);

	void onExpired(Event event);

	void onInited(Event event);

}
