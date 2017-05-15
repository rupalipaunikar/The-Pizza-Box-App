package com.pizzaboxcore.custom.exception;

public class OrderGenerationException extends Exception {
	private static final long serialVersionUID = 1L;

	public OrderGenerationException(String errorMessage, Throwable exception) {
		super(errorMessage,exception);
	}

	public OrderGenerationException(String errorMessage) {
		super(errorMessage);
	}
	
	public OrderGenerationException(Throwable exception) {
		super(exception);
	}
	
}
