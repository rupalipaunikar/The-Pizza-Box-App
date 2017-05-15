package com.payment.data;

import com.payment.exception.ErrorCode;

/**
 * This class holds the result status for the payment
 * 
 * @author rupalip
 *
 */
public class PaymentResult {

	private PaymentResultStatus paymentResultStatus;
	private ErrorCode errorCode;

	public PaymentResult() {}
	
	public PaymentResult(PaymentResultStatus paymentResultStatus, ErrorCode errorCode) {
		super();
		this.paymentResultStatus = paymentResultStatus;
		this.errorCode = errorCode;
	}

	public PaymentResultStatus getPaymentResultStatus() {
		return paymentResultStatus;
	}

	public void setPaymentResultStatus(PaymentResultStatus paymentResultStatus) {
		this.paymentResultStatus = paymentResultStatus;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "PaymentResult [paymentResultStatus=" + paymentResultStatus + ", errorCode=" + errorCode + "]";
	}
}
