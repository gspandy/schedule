package com.kanven.schedual.exactor.quartz;

public class JobException extends Exception {

	private static final long serialVersionUID = -762963753998189801L;

	public JobException() {
		super();
	}

	public JobException(String message, Throwable e) {
		super(message, e);
	}

	public JobException(String message) {
		super(message);
	}

	public JobException(Throwable e) {
		super(e);
	}

	
	
}
