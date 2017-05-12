package com.payment.exception;

/**
 * This represents exceptions that occur while payment validation
 * 
 * @author rupalip
 *
 */
public class PaymentValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public PaymentValidationException(String message){
		super(message);
	}
}
