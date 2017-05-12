package com.payment.exception;

/**
 * This represents DAO layer exceptions
 * 
 * @author rupalip
 *
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public DAOException(String message){
		super(message);
	}
}
