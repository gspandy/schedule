package com.kanven.schedual.exception;

public class SchedualException extends Exception {

	private static final long serialVersionUID = -3022206292756083896L;

	public SchedualException() {
		super();
	}

	public SchedualException(String message, Throwable e) {
		super(message, e);
	}

	public SchedualException(String message) {
		super(message);
	}

	public SchedualException(Throwable e) {
		super(e);
	}

}
