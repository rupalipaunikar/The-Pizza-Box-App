package com.payment.exception;

/**
 * This represents service layer exceptions
 * 
 * @author rupalip
 *
 */
public class PaymentServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public PaymentServiceException(String message){
		super(message);
	}
	
	public PaymentServiceException(Throwable e){
		super(e);
	}
}