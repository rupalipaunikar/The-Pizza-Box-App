package com.payment.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.payment.data.PaymentDetails;
import com.payment.gateway.PaymentGateway;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;

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

	/**
	 * This API is consumed by the core module and allows the user to choose the
	 * mode of payment
	 * 
	 * @param order
	 * @return order with PaymentMode.jsp
	 */
	@RequestMapping(value = "/makepayment", method = RequestMethod.POST)
	public ModelAndView makePayment(@ModelAttribute(Constants.ORDER) Order order) {
		// LOG.info("Prompting the user["+ order.getUser().getFullName() +"] to
		// select the payment mode");

		ModelAndView modelAndView = new ModelAndView(Constants.PAYMENT_MODE);
		modelAndView.addObject(Constants.ORDER, order);
		return modelAndView;
	}

	/**
	 * This API is to trigger the actual payment flow in the system
	 * and prompt the user to enter card details if he has selected 
	 * online mode
	 * 
	 * @param order
	 * @return 
	 */
	@RequestMapping(value = "/initiate", method = RequestMethod.POST)
	public ModelAndView initatePaymentFlow(@ModelAttribute(Constants.ORDER) Order order) {
		// LOG.info("Initiating the payment flow for
		// user["+order.getUser().getFullName()+"]");
		
		String result = paymentGateway.makePayment(order);
		System.out.println("cashhhhhhhhhhhh    "+result);
		if(order.getPaymentType() == PaymentType.CASH){
			return new ModelAndView("PaymentCash");
		}
		
		PaymentDetails paymentDetails = new PaymentDetails(new CardDetails(), order.getTotalAmount());
		ModelAndView mv = new ModelAndView(result);
		mv.addObject("paymentDetails", paymentDetails);
		return mv;
	}
}
