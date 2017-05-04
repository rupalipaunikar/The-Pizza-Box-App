package com.pizzabox.common.constants;

import org.apache.log4j.Logger;


/**
 * This enum holds values for the status which an order can have throughout its processing
 * 
 * @author rupalip
 *
 */
public enum Status {

	SUBMITTED(0), PAID_ONLINE(1), PAID_CASH(2), READY(3), DELIVERED(4), FAILED(5), IN_PROGRESS(6);

	private static final Logger LOG = Logger.getLogger(Status.class);
			
	private int status;

	private Status(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * This is a utility method to return the Status based on the 
	 * incoming int value
	 * 
	 * @param status
	 * @return Status
	 */
	public static Status getStatus(int status) {
		Status[] statuses = Status.values();

		for (Status st : statuses) {
			if (st.getStatus() == status) {
				return st;
			}
		}
		String message = "Status["+status+"] is invalid.";
		LOG.error(message);
		throw new IllegalArgumentException(message);
	}
}
