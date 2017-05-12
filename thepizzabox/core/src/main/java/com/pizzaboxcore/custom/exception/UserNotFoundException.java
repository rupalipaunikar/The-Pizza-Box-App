package com.pizzaboxcore.custom.exception;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public UserNotFoundException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public UserNotFoundException(Throwable e ){
		super(e);
	}
}
