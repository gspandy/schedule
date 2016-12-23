package com.kanven.schedual.register;

public class RegisterException extends Exception {

	private static final long serialVersionUID = -1607812207839305902L;

	public RegisterException() {
		super();
	}

	public RegisterException(String message, Throwable e) {
		super(message, e);
	}

	public RegisterException(String message) {
		super(message);
	}

	public RegisterException(Throwable e) {
		super(e);
	}

}
