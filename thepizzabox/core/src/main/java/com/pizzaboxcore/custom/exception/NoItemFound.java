package com.pizzaboxcore.custom.exception;
/**
 * 
 * @author Roshni
 *
 */
public class NoItemFound extends Exception {
	private static final long serialVersionUID = 1L;

	public NoItemFound(String errorMessage, Throwable exception) {
		super(errorMessage,exception);
	}

	public NoItemFound(String errorMessage) {
		super(errorMessage);
	}
	
	public NoItemFound(Throwable exception) {
		super(exception);
	}

}
