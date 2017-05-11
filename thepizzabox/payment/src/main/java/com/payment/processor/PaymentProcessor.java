package com.payment.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.payment.data.PaymentDetails;
import com.payment.data.PaymentResultStatus;
import com.payment.exception.PaymentProcessException;
import com.payment.exception.PaymentServiceException;
import com.payment.service.PaymentService;
import com.payment.validator.ValidationUtils;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;

/**
 * This class is responsible for carrying out the payment process
 * 
 * @author rupalip
 *
 */
@Component
public class PaymentProcessor {

	private static final Logger LOG = Logger.getLogger(PaymentProcessor.class);
	
	@Autowired
	private PaymentService paymentService;

	@Autowired
	private ValidationUtils validationUtils;

	/**
	 * This API carries out actual payment processing by deducting the balance and
	 * updating the order status and populating the result
	 * 
	 * @param paymentDetails
	 * @return paymentDetails
	 * @throws PaymentProcessException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public PaymentDetails makePayment(PaymentDetails paymentDetails) throws PaymentProcessException {
		Order order = null;
		
		try {
			LOG.info("Validating payment details");
			validationUtils.getPaymentValidator().validate(paymentDetails);
			
			order = paymentDetails.getOrder();
			
			paymentService.executePayment(paymentDetails);
			paymentService.updateOrderStatus(paymentDetails.getOrder().getId(), Status.PAID_ONLINE);
			
			paymentDetails.setPaymentResultStatus(PaymentResultStatus.SUCCESS);
			return paymentDetails;
		} 
		catch (PaymentServiceException e) {
			LOG.error("Error occurred while processing payment for order ID["+order.getId()+"]");
			throw new PaymentProcessException(e);
		}
	}
}
