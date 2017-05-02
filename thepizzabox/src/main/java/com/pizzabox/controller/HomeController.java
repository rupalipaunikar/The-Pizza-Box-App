package com.pizzabox.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pizzabox.model.Item;

@Controller
public class HomeController {
	
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

		Item item = new Item();
		item.setItemId(1);
		item.setName("Margherita");
		item.setPrice(250.00);
		item.setType("Pizza");
		
		Item item2 = new Item();
		item2.setItemId(1);
		item2.setName("Americano");
		item2.setPrice(250.00);
		item2.setType("Pizza");
		
		List<Item> pizzaList = new ArrayList<Item>();
		pizzaList.add(item);
		pizzaList.add(item2);
		
		Item beverage = new Item();
		beverage.setItemId(2);
		beverage.setName("Pepsi");
		beverage.setPrice(50.00);
		beverage.setType("Beverage");
		
		Item beverage2 = new Item();
		beverage2.setItemId(2);
		beverage2.setName("Sprite");
		beverage2.setPrice(50.00);
		beverage2.setType("Beverage");
		
		List<Item> beverageList = new ArrayList<Item>();
		beverageList.add(beverage);
		beverageList.add(beverage2);
		
		Item sides = new Item();
		sides.setItemId(3);;
		sides.setName("Pasta");
		sides.setPrice(150.00);
		sides.setType("Sides");
		
		Item sides2 = new Item();
		sides2.setItemId(3);;
		sides2.setName("Garlic Bread");
		sides2.setPrice(150.00);
		sides2.setType("Sides");
		
		List<Item> sidesList = new ArrayList<Item>();
		sidesList.add(sides);
		sidesList.add(sides2);
		
		Map<String,List<Item>> itemTypeMap = new HashMap<String, List<Item>>();
		itemTypeMap.put("Pizza", pizzaList);
		itemTypeMap.put("Beverage", beverageList);
		itemTypeMap.put("Sides", sidesList);
		
		
		return new ModelAndView("homepage","ItemTypeMap",itemTypeMap);
	}
	
	/*@RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
	public ModelAndView makeOrder(@ModelAttribute("itemsList") List itemList) {

		ModelAndView model = new ModelAndView();
		System.out.println("In makeorder");
		System.out.println(itemList.get(0));

		model.setViewName("login");
		return model;

	}*/
	
	@RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
	public ModelAndView makeOrder() {

		return new ModelAndView("login");

	}
	

}
