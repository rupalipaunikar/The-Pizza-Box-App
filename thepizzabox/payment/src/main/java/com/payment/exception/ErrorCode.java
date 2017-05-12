package com.payment.exception;

public enum ErrorCode {

	INSUFFICIENT_BALANCE(100, "You have insufficient balance"), 
	PAYMENT_PROCESS     (101,"Something went wrong while processing payment"),
	BALANCE_UNAVAILABLE (103, "Your balance is unavailable"),
	BALANCE_UPDATE (104, "Could not update your balance");

	private final int code;
	private final String description;

	private ErrorCode(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}
