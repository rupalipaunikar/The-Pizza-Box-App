package com.pizzaboxcore.custom.exception;

public class DAOException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public DAOException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
