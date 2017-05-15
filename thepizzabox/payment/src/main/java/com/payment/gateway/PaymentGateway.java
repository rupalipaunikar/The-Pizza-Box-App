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
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @return invoice
	 * 			Invoice containing order or error details and the payment status
	 */
	Invoice processPayment(PaymentDetails paymentDetails);
}
