package com.pizzaboxcore.dao;

import com.pizzabox.common.constants.Status;
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
	 * This API generates and returns the Order once it is stored in the database
	 * 
	 * @return User
	 * @throws DAOException 
	 */
	public Order generateOrder(Order order) throws DAOException;
	
	/**
	 * This API returns the Order with the given id from the database
	 * 
	 * @return User
	 * @throws DAOException 
	 */
	public Order getGeneratedOrder(Integer orderId) throws DAOException;
	
	/**
	 * This API updates the order status with the given status
	 * 
	 * @param orderId
	 * 			ID of the order being processed
	 * @param status
	 * 			Order status to be updated
	 * @throws DAOException 
	 */
	void updateOrderStatus(final Integer orderId, final Status status) throws DAOException;
}
