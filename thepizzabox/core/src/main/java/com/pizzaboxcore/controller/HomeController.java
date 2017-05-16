package com.pizzaboxcore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.request.ItemWrapper;
import com.pizzaboxcore.custom.exception.NoItemFound;
import com.pizzaboxcore.custom.exception.OrderGenerationException;
import com.pizzaboxcore.custom.exception.UserNotFoundException;
import com.pizzaboxcore.service.ItemService;
import com.pizzaboxcore.service.OrderService;
import com.pizzaboxcore.validator.ValidationUtils;

/**
 * HomeController is the base controller taking care of User Login , Generating
 * the order based on User data and sending the Order to Payment Module
 * 
 * @author Roshni
 */

@Controller
public class HomeController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ValidationUtils validationUtils;


	/**
	 * This method takes care of the User login
	 * 
	 * @return ModelAndView T
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) final String error,
			@RequestParam(value = "logout", required = false) final String logout) {

		final ModelAndView model = new ModelAndView();
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
	 * @throws NoItemFound 
	 */
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView showHomePage() throws NoItemFound {
		final List<Item> itemList = (List<Item>) itemService.createInitialList();
		validationUtils.getItemValidator().validateItemList(itemList);

		final ItemWrapper itemWrapper = new ItemWrapper();
		itemWrapper.setItemList(itemList);
		return new ModelAndView("userhomepage", "itemWrapper", itemWrapper);
	}

	/**
	 * This method creates an order based on the items selected by the user
	 * 
	 * @param itemWrapper
	 *            This contains all the items(Pizza,Beverage,Sides)details
	 *            selected by the user
	 * @param request
	 *            This is the Http request containing the complete request
	 *            coming from /makeorder
	 * @return ModelAndView
	 * @throws UserNotFoundException 
	 * @throws OrderGenerationException 
	 * @throws NoItemFound 
	 */
	@RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
	public String makeOrder(@ModelAttribute("itemWrapper") final ItemWrapper itemWrapper,
			final Model model,@RequestParam("itemCheckBox")final String[] itemCheckBox) throws OrderGenerationException, UserNotFoundException, NoItemFound {
		
		validationUtils.getItemValidator().validateItemWrapper(itemWrapper);

		final List<Integer> checkBoxList = new ArrayList<Integer>();
		for (int m = 0; m < itemCheckBox.length; m++) {
			checkBoxList.add(Integer.parseInt(itemCheckBox[m]));
		}
		final List<Item> finalItemList = orderService.calculateFinalOrderList(itemWrapper.getItemList(), checkBoxList);
		
		final Order order = orderService.generateOrder(finalItemList);
		model.addAttribute("order",order);
		model.addAttribute("finalItemList",finalItemList);
		return "paymentgateway";
	}
}
