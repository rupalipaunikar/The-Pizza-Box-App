package com.pizzaboxcore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.service.ItemService;

@Controller
public class HomeController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) final String error,
			@RequestParam(value = "logout", required = false) final String logout) {

		System.out.println("In Login");
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}
	
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView showHomePage() {

		Map<String,List<Item>> itemTypeMap =  itemService.createInitialItemDataMap();
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + itemTypeMap.get(ItemType.PIZZA.toString()));
		
		return new ModelAndView("homepage","ItemList",itemTypeMap.get(ItemType.PIZZA.toString()));
	}
	
	
	
	@RequestMapping(value = "/makeorder", method = RequestMethod.POST)
	public ModelAndView makeOrder(@ModelAttribute("item") ArrayList<Item> item,HttpServletRequest req) {
		System.out.println("In home controller....");
		
		Order order = new Order();
		order.setId(1);
		order.setStatus(Status.SUBMITTED);
		
		User user = new User();
		user.setFirstName("Rupali");
		user.setLastName("Paunikar");
		user.setContactNo("98989");
		user.setAddress("Pune");
		
		order.setUser(user);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("order",order);
		mv.setViewName("paymentgateway");
		return mv;
	}
}
