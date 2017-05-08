package com.payment.service;

import com.payment.data.PaymentDetails;

public interface PaymentService {

	public String executePayment(PaymentDetails paymentDetails);
}
