package com.pizzaboxcore.dao;

import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;

/**
 * OrderDao contains the APIs to interact with order table
 * 
 * @author Roshni
 *
 */
public interface OrderDAO {

	/**
	 * This API returns the complete details of Logged In user
	 * 
	 * @return User
	 */
	public User getUserDetails(String userName);
	
	/**
	 * This API returns the Order once it is stored in the database
	 * 
	 * @return User
	 */
	public Order generateOrder(Order order);
}
