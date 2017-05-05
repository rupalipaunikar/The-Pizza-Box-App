package com.pizzaboxcore.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzabox.common.request.ItemWrapper;
import com.pizzaboxcore.service.ItemService;
import com.pizzaboxcore.service.OrderService;



/**
 * HomeController is the base controller taking care of User Login , Generating
 * the order based on User data and sending the Order to Payment Module
 * 
 * @author Roshni
 *
 */

@Controller
public class HomeController {


	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderService orderService;

	/**
	 * This method takes care of the User login
	 * 
	 * @return ModelAndView T
	 */
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

	/**
	 * This method returns the User HomePage once the login is successfull
	 * 
	 * @return ModelAndView The Model contains all the items offered by the
	 *         Pizza Box System
	 */
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView showHomePage() {
		List<Item> itemTypeMap = (List<Item>) itemService.createInitialList();
		ItemWrapper itemWrapper = new ItemWrapper();
		itemWrapper.setItemList(itemTypeMap);
		return new ModelAndView("homepage", "itemWrapper", itemWrapper);
	}

	/**
	 * This method creates an order based on the items selected by the user
	 * 
	 * @param itemWrapper
	 *            This contains all the items(Pizza,Beverage,Sides)details
	 *            selected by the user
	 * @param request
	 *            This is the Http request containing the complete request coming
	 *            from /makeorder
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
	public ModelAndView makeOrder(@ModelAttribute("itemWrapper") final ItemWrapper itemWrapper,
			final HttpServletRequest request, Principal principalUserObject, ModelMap model) {

		final String[] selectedCheckBox = request.getParameterValues("itemCheckBox");
		List<Integer> checkBoxList = new ArrayList<Integer>();

		for (int m = 0; m < selectedCheckBox.length; m++) {
			checkBoxList.add(Integer.parseInt(selectedCheckBox[m]));
		}

		final List<Item> itemList = itemWrapper.getItemList();

		final List<Item> finalOrderList = new ArrayList<Item>();

		Iterator<Item> iterator = itemList.iterator();
		Iterator iteratorCheckBox = checkBoxList.iterator();

		int i = 0, j = 0;
		Double totalPrice = 0.0;
		while (iterator.hasNext() && iteratorCheckBox.hasNext() && (i != checkBoxList.size())
				&& (j != itemList.size())) {
			System.out.println(itemList.get(j));

			int itemId = itemList.get(j).getItemId();
			int checkBoxId = checkBoxList.get(i);

			if (itemId == checkBoxId) {
				totalPrice = totalPrice + itemList.get(j).getPrice();
				Item item = new Item();
				item.setItemId(itemList.get(j).getItemId());
				item.setName(itemList.get(j).getName());
				item.setPrice(itemList.get(j).getPrice());
				item.setQuantity(itemList.get(j).getQuantity());
				item.setType(itemList.get(j).getType());
				finalOrderList.add(item);
				i++;
				iteratorCheckBox.next();
			}
			iterator.next();
			j++;

		}

		final User user = orderService.getUserDetails(principalUserObject);
		Order order = orderService.generateOrder(finalOrderList, totalPrice, user);
		return new ModelAndView("paymentgateway", "order", order);



	}

}
