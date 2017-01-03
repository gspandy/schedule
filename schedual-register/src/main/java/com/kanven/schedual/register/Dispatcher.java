package com.kanven.schedual.register;

interface Dispatcher {

	public void addListener(Listener listener);

	public void remove(Listener listener);

	public void dispatch(Event event);

	public void clear();

}
