package com.payment.gateway;

import com.payment.data.Invoice;
import com.payment.data.PaymentDetails;

/**
 * This class acts as a gateway to payment-flow.xml to initiate the
 * payment 
 * 
 * @author rupalip
 *
 */
public interface PaymentGateway {

	/**
	 * This API initiates the payment flow 
	 * 
	 * @param order
	 * @return invoice
	 */
	Invoice processPayment(PaymentDetails paymentDetails);
}
