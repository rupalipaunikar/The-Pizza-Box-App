package com.payment.exception;

/**
 * This represents exceptions that occur while payment processing
 * 
 * @author rupalip
 *
 */
public class PaymentProcessException extends Exception {

	private static final long serialVersionUID = 1L;

	public PaymentProcessException(String message){
		super(message);
	}
	
	public PaymentProcessException(Throwable e){
		super(e);
	}
}
