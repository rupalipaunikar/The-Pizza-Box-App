package com.payment.gateway;

import org.springframework.web.servlet.ModelAndView;

import com.pizzabox.common.model.Order;

/**
 * Payment Gateway acts as a gateway to payment-flow.xml to initiate the
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
	 * @return success | failure
	 */
	String makePayment(Order order);
}
