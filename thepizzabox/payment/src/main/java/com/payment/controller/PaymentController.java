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

	private static final Logger LOG = Logger.getLogger(PaymentController.class);

	@Autowired
	private PaymentGateway paymentGateway;

	@Autowired
	private ValidationUtils validationUtils;

	/**
	 * This API is consumed by the core module and allows the user to choose the
	 * mode of payment and enter card details if online mode is selected
	 * 
	 * @param order
	 * @param user
	 * @return order with mode of payment selection for the user
	 * @throws PaymentValidationException
	 */
	@RequestMapping(value = "/makepayment", method = RequestMethod.POST)
	public ModelAndView makePayment(@ModelAttribute(Constants.ORDER) final Order order,
			@ModelAttribute(Constants.USER) final User user) throws PaymentValidationException {

		validationUtils.getPaymentValidator().validate(order, user);

		LOG.info("Prompting user[" + user.getUsername() + "] to select the mode of payment");

		ModelAndView modelAndView = new ModelAndView(Constants.PAYMENT_MODE);
		modelAndView.addObject(Constants.ORDER, order);
		modelAndView.addObject(Constants.USER, user);
		modelAndView.addObject(Constants.CARD_DETAILS, new CardDetails());
		return modelAndView;

	}

	/**
	 * This API triggers the actual payment flow in the system
	 * 
	 * @param order
	 * @param user
	 * @param cardDetails
	 * @param model
	 * @return invoice view
	 * @throws PaymentValidationException
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String initatePaymentFlow(@ModelAttribute(Constants.ORDER) final Order order,
			@ModelAttribute(Constants.USER) final User user,
			@ModelAttribute(Constants.CARD_DETAILS) final CardDetails cardDetails, final Model model)
			throws PaymentValidationException {

		// validate
		String error = validationUtils.getPaymentValidator().validate(order, user, cardDetails);
		
		if(!Strings.isNullOrEmpty(error) && !Constants.NO_ERROR.equalsIgnoreCase(error)){
			model.addAttribute(Constants.ERROR, error);
			return Constants.PAYMENT_MODE;
		}
		
		String resultView = triggerPaymentFlow(order, cardDetails, user, model);
		return resultView; 
	}

	/**
	 * This method triggers the spring messaging flow for payment execution
	 * 
	 * @param order
	 * @param cardDetails
	 * @param user
	 * @param model
	 * @return payment result view
	 */
	private String triggerPaymentFlow(Order order, CardDetails cardDetails, User user, Model model) {
		// prepare payment details to be processed
		cardDetails.setUser(user);
		PaymentDetails paymentDetails = new PaymentDetails(cardDetails, order, new PaymentResult());

		String username = user.getUsername();
		LOG.info("Initiating the payment flow for user[" + username + "]");
		// call the payment-flow
		Invoice invoice = paymentGateway.makePayment(paymentDetails);
		LOG.info("Payment flow is complete for user[" + username + "]");
		
		model.addAttribute(Constants.INVOICE, invoice);
		return Constants.PAYMENT_RESULT;
	}
	
	

	/**
	 * This is the exception handler for PaymentRequestException
	 * 
	 * @param request
	 * @param ex
	 * @return error page
	 */
	@ExceptionHandler(PaymentValidationException.class)
	public ModelAndView handlePaymentValidationException(HttpServletRequest request, Exception ex) {
		LOG.error("Requested URL:- " + request.getRequestURL());
		LOG.error("Exception Raised:- " + ex);

		ModelAndView modelAndView = new ModelAndView(Constants.ERROR);
		modelAndView.addObject(Constants.EXCEPTION, ex);
		modelAndView.addObject(Constants.URL, request.getRequestURL());
		return modelAndView;
	}

	/**
	 * This is the exception handler for PaymentProcessException
	 * 
	 * @param request
	 * @param ex
	 * @return error page
	 */
	@ExceptionHandler(PaymentProcessException.class)
	public ModelAndView handlePaymentProcessException(HttpServletRequest request, Exception ex) {
		LOG.error("Requested URL:- " + request.getRequestURL());
		LOG.error("Exception Raised:- " + ex);

		ModelAndView modelAndView = new ModelAndView(Constants.ERROR);
		modelAndView.addObject(Constants.EXCEPTION, ex);
		modelAndView.addObject(Constants.URL, request.getRequestURL());
		return modelAndView;
	}
}
