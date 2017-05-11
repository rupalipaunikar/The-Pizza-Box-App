package com.payment.service;

import com.payment.data.PaymentDetails;
import com.payment.exception.PaymentServiceException;
import com.pizzabox.common.constants.Status;

/**
 * PaymentService contains the APIs to interact with Payment related services
 * 
 * @author rupalip
 *
 */
public interface PaymentService {

	/**
	 * This API carries out the actual processing of the payment
	 *  
	 * @param paymentDetails
	 * @throws PaymentServiceException 
	 */
	void executePayment(PaymentDetails paymentDetails) throws PaymentServiceException;
	
	/**
	 * This API updates the order status with the given status parameter
	 * 
	 * @param orderId
	 * @param status
	 * @throws PaymentServiceException 
	 */
	void updateOrderStatus(Integer orderId, Status status) throws PaymentServiceException;
}
