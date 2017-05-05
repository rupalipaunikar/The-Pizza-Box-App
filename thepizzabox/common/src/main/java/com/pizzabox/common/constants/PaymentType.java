package com.pizzabox.common.constants;

import org.apache.log4j.Logger;

/**
 * This enum holds values for the payment method which a user can opt for
 * 
 * @author rupalip
 *
 */
public enum PaymentType {

	CASH(0), ONLINE(1), NOTSELECTED(2);

	private static final Logger LOG = Logger.getLogger(PaymentType.class);
	
	private int type;
	
	private PaymentType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * This is a utility method to return the PaymentType enum based on the 
	 * incoming int value
	 * 
	 * @param type
	 * @return PaymentType
	 */
	public static PaymentType getPaymentType(int type){
		PaymentType[] types = PaymentType.values();
		
		for(PaymentType pType : types){
			if(pType.getType() == type){
				return pType;
			}
		}
		String message = "PaymentType["+type+"] is invalid.";
		LOG.error(message);
		throw new IllegalArgumentException(message);
	}
}
