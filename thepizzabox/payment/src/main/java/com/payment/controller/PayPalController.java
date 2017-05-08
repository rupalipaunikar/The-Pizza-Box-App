package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.payment.data.PaymentDetails;
import com.payment.service.PaymentService;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.model.Order;

@RestController
public class PayPalController {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "/paypal/makepayment", method = RequestMethod.POST)
	public String makePayment(@RequestBody Order order) {
		System.out.println("In paypal controller  - " + order);
		return Constants.PAYMENT_DETAILS;
	}

	@RequestMapping(value = "/paypal/submit", method = RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute("paymentDetails") PaymentDetails paymentDetails) {

		System.out.println("In paypal controller submit - " + paymentDetails);

		String result = paymentService.executePayment(paymentDetails);

		if (Constants.SUCCESS.equals(result)) {
			//LOG.info("Transaction is successful");
			return new ModelAndView(Constants.PAYMENT_SUCCESS);
		} else if (Constants.FAILURE.equals(result)) {
			//LOG.info("Transaction failed");
			return new ModelAndView(Constants.PAYMENT_FAILURE);
		} else {
			//LOG.info("Something went wrong while processing");
			return new ModelAndView(Constants.SOMETHING_WENT_WRONG);
		}
	}
}
