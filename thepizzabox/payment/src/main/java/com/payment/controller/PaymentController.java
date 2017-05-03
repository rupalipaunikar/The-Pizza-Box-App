package com.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





@Controller
public class PaymentController {

	@RequestMapping(value="/payment", method=RequestMethod.POST)
	public ModelAndView makePayment(){
		ModelAndView modelAndView = new ModelAndView("payment");
		System.out.println("In payment...");
		return modelAndView;
		
	}
}
