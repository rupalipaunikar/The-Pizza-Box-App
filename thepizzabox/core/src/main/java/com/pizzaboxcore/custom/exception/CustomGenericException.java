package com.pizzaboxcore.custom.exception;
/**
 * 
 * @author Roshni
 *
 */
public class CustomGenericException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public CustomGenericException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
