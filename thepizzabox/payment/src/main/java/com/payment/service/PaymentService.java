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
	 * 			PaymentDetails containing order, user and card details
	 * @throws PaymentServiceException 
	 */
	void executePayment(PaymentDetails paymentDetails) throws PaymentServiceException;
	
	/**
	 * This API updates the order status with the given status parameter
	 * 
	 * @param orderId
	 * 			ID of the order to be updated
	 * @param status
	 * 			Order status to update with
	 * @throws PaymentServiceException 
	 */
	void updateOrderStatus(Integer orderId, Status status) throws PaymentServiceException;
}
