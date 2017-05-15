package com.pizzaboxcore.service.impl;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.SubOrder;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.custom.exception.DAOException;
import com.pizzaboxcore.custom.exception.OrderGenerationException;
import com.pizzaboxcore.custom.exception.UserNotFoundException;
import com.pizzaboxcore.dao.OrderDAO;
import com.pizzaboxcore.order.generator.OrderGenerator;
import com.pizzaboxcore.service.OrderService;

/**
 * OrderServiceImpl contains the implementation of OrderService APIs
 * 
 * @author Roshni
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private OrderGenerator orderGenerator;

	/**
	 * This method creates an order based on the item select by the user and
	 * return the Order object created
	 * 
	 * @param List<Item>
	 *            This contains all the items(Pizza,Beverage,Sides)details
	 *            selected by the user
	 * @param totalPrice
	 *            This is the total Price of the order
	 * @return Order
	 * @throws UserNotFoundException 
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public Order generateOrder(final List<Item> finalItemList) throws OrderGenerationException, UserNotFoundException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String username = auth.getName();
		Order finalOrder=null;
		LOG.info("Order Creation Started for the user" + username);

		final User user = getUserDetails(username);
		final Order raworder = orderGenerator.generateOrder(finalItemList, user);

		final Order order = orderGenerator.generateOrder(finalItemList, user);
		try{
		finalOrder = orderDAO.generateOrder(order);
		}catch(DAOException exception){
			LOG.error("Could not generate order for user ["+user.getUsername()+"].");
			throw new OrderGenerationException(exception);
		}

		LOG.info("Order Created for the user " + order.getUser().getUsername());
		return finalOrder;
	}

	/**
	 * This method is used to get the complete details of the Logged in user
	 * 
	 * @param user
	 *            This Principal object contains the name of the logged in user
	 * @return User
	 * @throws UserNotFoundException 
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public User getUserDetails(final String username) throws UserNotFoundException {

		LOG.info("Fetching Details for " + username + " from the database");
		User user;
		try {
			user = orderDAO.getUserDetails(username);
		} catch (DAOException exception) {
			LOG.error("No Details found for the user ["+username+"]");
			throw new UserNotFoundException(exception);
		}
		return user;
	}

	/**
	 * This method creates a final list of item based on user selection
	 * 
	 * @param List<Item>
	 *            This contains all the items(Pizza,Beverage,Sides)details
	 *            selected by the user
	 * @param List<Integer>
	 * @return List<Item>
	 */
	public List<Item> calculateFinalOrderList(final List<Item> itemList, final List<Integer> checkBoxList) {

		LOG.info("Started Creating Final Order List");
		final List<Item> finalOrderList = new ArrayList<Item>();
		final Iterator<Item> iterator = itemList.iterator();
		Iterator iteratorCheckBox = checkBoxList.iterator();

		int checkBoxCounter = 0, itemListCounter = 0;
		while (iterator.hasNext() && iteratorCheckBox.hasNext() && (checkBoxCounter != checkBoxList.size())
				&& (itemListCounter != itemList.size())) {
			System.out.println(itemList.get(itemListCounter));

			int itemId = itemList.get(itemListCounter).getItemId();
			int checkBoxId = checkBoxList.get(checkBoxCounter);

			if (itemId == checkBoxId) {
				Item item = new Item();
				item.setItemId(itemList.get(itemListCounter).getItemId());
				item.setName(itemList.get(itemListCounter).getName());
				item.setPrice(itemList.get(itemListCounter).getPrice());
				item.setQuantity(itemList.get(itemListCounter).getQuantity());
				item.setType(itemList.get(itemListCounter).getType());
				finalOrderList.add(item);
				checkBoxCounter++;
				iteratorCheckBox.next();
			}
			iterator.next();
			itemListCounter++;

		}
		LOG.info("Final Order List Created");
		return finalOrderList;
	}

}
