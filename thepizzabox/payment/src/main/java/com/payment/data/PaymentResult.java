package com.payment.data;

import com.payment.exception.ErrorCode;

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

}
