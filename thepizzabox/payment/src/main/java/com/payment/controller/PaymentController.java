package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.payment.gateway.PaymentGateway;
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

	@Autowired
	private PaymentGateway paymentGateway;
	
	@RequestMapping(value="/makepayment", method=RequestMethod.POST)
	public ModelAndView makePayment(@ModelAttribute("order")Order order,@ModelAttribute("user") User user){
		
		System.out.println("in payment controller");
		paymentGateway.makePayment(order);
		return null;
	}
	
	public void name() {
		
	}
}
