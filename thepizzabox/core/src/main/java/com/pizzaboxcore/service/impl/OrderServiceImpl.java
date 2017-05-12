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

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.SubOrder;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.dao.OrderDAO;
import com.pizzaboxcore.order.generator.OrderGenerator;
import com.pizzaboxcore.service.OrderService;
import com.pizzaboxcore.validator.UserValidator;

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
	 */
	public Order generateOrder(final List<Item> finalItemList) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String username = auth.getName();
		LOG.info("Order Creation Started for the user" + username);

		final User user = getUserDetails(username);

		final Order order = orderGenerator.generateOrder(finalItemList, user);

		LOG.info("Order Created for the user " + order.getUser().getUsername());
		return order;
	}

	/**
	 * This method is used to get the complete details of the Logged in user
	 * 
	 * @param user
	 *            This Principal object contains the name of the logged in user
	 * @return User
	 */
	public User getUserDetails(final String username) {

		LOG.info("Fetching Details for " + username + " from the database");
		final User user = orderDAO.getUserDetails(username);
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
		Iterator<Item> iterator = itemList.iterator();
		Iterator iteratorCheckBox = checkBoxList.iterator();

		int i = 0, j = 0;
		while (iterator.hasNext() && iteratorCheckBox.hasNext() && (i != checkBoxList.size())
				&& (j != itemList.size())) {
			System.out.println(itemList.get(j));

			int itemId = itemList.get(j).getItemId();
			int checkBoxId = checkBoxList.get(i);

			if (itemId == checkBoxId) {
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
		LOG.info("Final Order List Created");
		return finalOrderList;
	}

	

}
