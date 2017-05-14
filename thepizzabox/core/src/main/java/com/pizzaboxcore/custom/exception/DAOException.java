package com.pizzaboxcore.custom.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public DAOException(String errorMessage, Throwable exception) {
		super(errorMessage,exception);
	}

	public DAOException(String errorMessage) {
		super(errorMessage);
	}
	
	public DAOException(Throwable exception) {
		super(exception);
	}
	

}
