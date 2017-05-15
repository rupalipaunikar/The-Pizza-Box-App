package com.pizzaboxcore.dao;

import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.custom.exception.DAOException;

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
	 * @throws DAOException 
	 */
	public User getUserDetails(String userName) throws DAOException;
	
	/**
	 * This API returns the Order once it is stored in the database
	 * 
	 * @return User
	 * @throws DAOException 
	 */
	public Order generateOrder(Order order) throws DAOException;
}
