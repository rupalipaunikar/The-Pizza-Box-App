package com.payment.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.payment.data.PaymentDetails;
import com.payment.exception.PaymentProcessException;
import com.payment.exception.PaymentValidationException;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;

/**
 * Validator class for payment module
 * 
 * @author rupalip
 *
 */
@Component
public class PaymentValidator {

	private static final Logger LOG = Logger.getLogger(PaymentValidator.class);

	/**
	 * Validates order
	 * 
	 * @param order
	 * 			Order being processed
	 * @throws PaymentValidationException
	 */
	public void validate(final Order order) throws PaymentValidationException {
		LOG.info("Validating order information.");
		if (order == null || order.getId() == null) {
			String errMsg = "Either order or its ID is null";
			LOG.error(errMsg);
			throw new PaymentValidationException(errMsg);
		}

		final Double totalAmount = order.getTotalAmount();
		if (totalAmount == null || totalAmount == 0.0) {
			String errMsg = "Order ID[" + order.getId() + "] has invalid amount";
			LOG.error(errMsg);
			throw new PaymentValidationException(errMsg);
		}

		LOG.info("Order validation complete");
	}

	/**
	 * Validates user
	 * 
	 * @param user
	 * 			User for whom order is being processed
	 * @throws PaymentValidationException
	 */
	public void validate(final User user) throws PaymentValidationException {
		LOG.info("Validating user information.");

		if (user == null) {
			String errMsg = "User cannot be null";
			LOG.error(errMsg);
			throw new PaymentValidationException(errMsg);
		}

		if (user.getUserId() == null) {
			String errMsg = "UserID cannot be null";
			LOG.error(errMsg);
			throw new PaymentValidationException(errMsg);
		}

		LOG.info("User validation complete");
	}

	/**
	 * Validates order and user and CardDetails
	 * 
	 * @param order
	 * 			Order being processed
	 * @param user
	 * 			User for whom order is being processed
	 * @param cardDetails
	 * 			Card details of the user
	 * @throws PaymentValidationException
	 */
	public String validate(final Order order, final User user, final CardDetails cardDetails) throws PaymentValidationException {
		validate(order);
		validate(user);
		final PaymentType paymentType = order.getPaymentType();
		
		if(paymentType == null || !(paymentType == PaymentType.CASH || paymentType == PaymentType.ONLINE)){
			String errMsg = "Payment type has not been selected for order ID[" + order.getId() + "]";
			LOG.error(errMsg);
			throw new PaymentValidationException(errMsg);
		}
		
		if (paymentType == PaymentType.ONLINE) {
			LOG.info("User has selected ONLINE payment mode. Validating card details");
			// validate card details and prompt the user to enter correct
			// information
			Set<ConstraintViolation<CardDetails>> cvCard = ValidationUtils.getValidator().validate(cardDetails);

			if (cvCard != null && !cvCard.isEmpty()) {
				for (ConstraintViolation<CardDetails> cv : cvCard) {
					return cv.getMessage();
				}
			}

			LOG.info("Card details entered are valid");
		}
		return Constants.NO_ERROR;
	}

	/**
	 * Validates order and user 
	 * 
	 * @param order
	 * 			Order being processed
	 * @param user
	 * 			User for whom order is being processed
	 * @throws PaymentValidationException
	 */
	public void validate(final Order order, final User user) throws PaymentValidationException {
		validate(order);
		validate(user);
	}
	
	/**
	 * Validate payment details
	 * 
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @throws PaymentProcessException
	 */
	public void validate(final PaymentDetails paymentDetails) throws PaymentProcessException {
		if (paymentDetails == null || paymentDetails.getCardDetails() == null 
				                   || paymentDetails.getOrder() == null
				                   || paymentDetails.getCardDetails().getUser() == null) {

			String errMsg = "Cannot execute payment as either card or order or user details are not available";
			LOG.error(errMsg);
			throw new PaymentProcessException(errMsg);

		}
	}
}
