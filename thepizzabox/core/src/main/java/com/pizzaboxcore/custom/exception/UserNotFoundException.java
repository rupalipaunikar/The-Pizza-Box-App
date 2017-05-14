package com.pizzaboxcore.custom.exception;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String errorMessage, Throwable exception) {
		super(errorMessage,exception);
	}

	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
	public UserNotFoundException(Throwable exception) {
		super(exception);
	}
}
