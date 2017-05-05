package com.pizzaboxcore.service;


import java.security.Principal;
import java.util.List;

import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;


/**
 * OrderService contains APIs to interact with Order related services
 * 
 * @author Roshni
 *
 */

public interface OrderService {

	/**
	 * This API creates an order based on the item select by the user and 
	 * return the Order object created 
	 * 	
	 * @param List<Item> This contains all the items(Pizza,Beverage,Sides)details selected by the user
	 * @param totalPrice This is the total Price of the order
	 * @return Order
	 */
	public Order generateOrder(List<Item> itemList,Double totalPrice,User user);
	
	/**
	 * This API is used to get the complete details of the Logged in user 
	 * 	
	 * @param user This Principal object contains the name of the logged in user 
	 * @return User
	 */
	public User getUserDetails(Principal user);
}
