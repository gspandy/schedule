package com.kanven.schedual.register;

import java.util.ArrayList;
import java.util.List;

class RegisterDispatcher implements Dispatcher {

	private List<Listener> listeners = new ArrayList<Listener>();

	public void addListener(Listener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void remove(Listener listener) {
		listeners.remove(listener);
	}

	public void dispatch(Event event) {
		for (Listener listener : listeners) {
			switch (event.getType()) {
			case CONNECTED:
				listener.onConnected(event);
				break;
			case DISCONNECTED:
				listener.onDisconnected(event);
				break;
			case EXPIRED:
				listener.onExpired(event);
				break;
			case INITED:
				listener.onInited(event);
				break;
			default:
				break;
			}
		}
	}

	public void clear() {
		listeners.clear();
	}

}
