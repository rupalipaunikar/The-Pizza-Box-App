package com.payment.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.payment.data.Invoice;
import com.payment.data.PaymentDetails;
import com.payment.data.PaymentResult;
import com.payment.exception.PaymentProcessException;
import com.payment.exception.PaymentValidationException;
import com.payment.gateway.PaymentGateway;
import com.payment.validator.PaymentValidator;
import com.payment.validator.ValidationUtils;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;

/**
 * Controller class for exposing payment related services
 * 
 * @author rupalip
 *
 */
@Controller
public class PaymentController {

	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(PaymentController.class);

	/**
	 * PaymentGateway - initiates the payment flow
	 */
	@Autowired
	private PaymentGateway paymentGateway;

	/**
	 * ValidationUtils - a utility class for various kinds of validation
	 */
	@Autowired
	private ValidationUtils validationUtils;
	
	/**
	 * This API is consumed by the core module and allows the user to choose the
	 * mode of payment and enter card details if online mode is selected
	 * 
	 * @param order
	 * 			Order received from the core module
	 * @param user
	 * 			User for whom the order is being processed
	 * @return modelAndView
	 * 			With Model as order, card details and user details 
	 * 			and PaymentMode view to enable payment type selection for the user
	 * @throws PaymentValidationException
	 */
	@RequestMapping(value = "/makepayment", method = RequestMethod.POST)
	public ModelAndView makePayment(@ModelAttribute(Constants.ORDER) final Order order,
			@ModelAttribute(Constants.USER) final User user) throws PaymentValidationException {

		final PaymentValidator paymentValidator = validationUtils.getPaymentValidator();
		paymentValidator.validate(order, user);

		LOG.info("Prompting user[" + user.getUsername() + "] to select the mode of payment");

		final ModelAndView modelAndView = new ModelAndView(Constants.PAYMENT_MODE);
		modelAndView.addObject(Constants.ORDER, order);
		modelAndView.addObject(Constants.USER, user);
		modelAndView.addObject(Constants.CARD_DETAILS, new CardDetails());
		return modelAndView;

	}

	/**
	 * This API triggers the actual payment flow in the system
	 * 
	 * @param order
	 * 			Order received after payment type selection
	 * @param user
	 * 			User for whom the payment is being processed
	 * @param cardDetails
	 * 			CardDetails entered by the user
	 * @param model
	 * 			Contains all attributes
	 * @return PaymentResult view 
	 * 			View where the invoice is displayed
	 * @throws PaymentValidationException
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String initatePaymentFlow(@ModelAttribute(Constants.ORDER) final Order order,
			@ModelAttribute(Constants.USER) final User user,
			@ModelAttribute(Constants.CARD_DETAILS) final CardDetails cardDetails, final Model model)
			throws PaymentValidationException {

		// validate
		final PaymentValidator paymentValidator = validationUtils.getPaymentValidator();
		final String error = paymentValidator.validate(order, user, cardDetails);
		
		if(!Strings.isNullOrEmpty(error) && !Constants.NO_ERROR.equalsIgnoreCase(error)){
			model.addAttribute(Constants.ERROR, error);
			return Constants.PAYMENT_MODE;
		}
		
		return triggerPaymentFlow(order, cardDetails, user, model);
	}

	/**
	 * This method triggers the spring messaging flow for payment execution
	 * 
	 * @param order
	 * 			Order received after payment type selection
	 * @param cardDetails
	 * 			CardDetails entered by the user
	 * @param user
	 * 			User for whom the payment is being processed
	 * @param model
	 * 			Contains all attributes
	 * @return PaymentResult view 
	 * 			View where the invoice is displayed
	 */
	private String triggerPaymentFlow(final Order order, final CardDetails cardDetails, final User user, final Model model) {
		// prepare payment details to be processed
		cardDetails.setUser(user);
		final PaymentDetails paymentDetails = new PaymentDetails(cardDetails, order, new PaymentResult());

		final String username = user.getUsername();
		LOG.info("Initiating the payment flow for user[" + username + "]");
		// call the payment-flow
		final Invoice invoice = paymentGateway.processPayment(paymentDetails);
		LOG.info("Payment flow is complete for user[" + username + "]");
		
		model.addAttribute(Constants.INVOICE, invoice);
		return Constants.PAYMENT_RESULT;
	}
	
	

	/**
	 * This is the exception handler for PaymentValidationException and returns 
	 * an error view when validation errors occur
	 * 
	 * @param request
	 * 			HTTP Request
	 * @param exception
	 * 			Exception occurred
	 * @return error view
	 * 			View displaying the error message
	 */
	@ExceptionHandler(PaymentValidationException.class)
	public ModelAndView handlePaymentValidationException(final HttpServletRequest request, final Exception exception) {
		LOG.error("Requested URL:- " + request.getRequestURL());
		LOG.error("Exception Raised:- " + exception);

		final ModelAndView modelAndView = new ModelAndView(Constants.ERROR);
		modelAndView.addObject(Constants.EXCEPTION, exception);
		modelAndView.addObject(Constants.URL, request.getRequestURL());
		return modelAndView;
	}

	/**
	 * This is the exception handler for PaymentProcessException and returns 
	 * an error view when errors occur during payment processing
	 * 
	 * @param request
	 * 			HTTP Request
	 * @param exception
	 * 			Exception occurred
	 * @return error view
	 * 			View displaying the error message
	 */
	@ExceptionHandler(PaymentProcessException.class)
	public ModelAndView handlePaymentProcessException(final HttpServletRequest request, final Exception exception) {
		LOG.error("Requested URL:- " + request.getRequestURL());
		LOG.error("Exception Raised:- " + exception);

		final ModelAndView modelAndView = new ModelAndView(Constants.ERROR);
		modelAndView.addObject(Constants.EXCEPTION, exception);
		modelAndView.addObject(Constants.URL, request.getRequestURL());
		return modelAndView;
	}
}
