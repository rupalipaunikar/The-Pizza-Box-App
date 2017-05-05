package com.pizzaboxcore.service.impl;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.SubOrder;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.dao.OrderDAO;
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

	public Order generateOrder(final List<Item> itemList, final Double totalPrice, final User user) {
		LOG.info("Creating Order for the user " + user.getUsername());

		List<SubOrder> subOrderList = new ArrayList<SubOrder>();

		Order order = new Order();
		
		Iterator<Item> iteratorItemList = itemList.iterator();
		int itemListCounter = 0;
		SubOrder pizzaSubOrder = new SubOrder();
		SubOrder beverageSubOrder = new SubOrder();
		SubOrder sidesSubOrder = new SubOrder();

		while (iteratorItemList.hasNext() && itemListCounter != itemList.size()) {

			if (itemList.get(itemListCounter).getType().equals(ItemType.PIZZA)) {
				pizzaSubOrder.setAmount(itemList.get(itemListCounter).getPrice()
						+ (pizzaSubOrder.getAmount() != null ? pizzaSubOrder.getAmount() : 0));
				pizzaSubOrder.setQuantity(itemList.get(itemListCounter).getQuantity()
						+ (pizzaSubOrder.getQuantity() != null ? pizzaSubOrder.getQuantity() : 0));
				pizzaSubOrder.setSubOrderType(ItemType.PIZZA);
				pizzaSubOrder.addToItems(itemList.get(itemListCounter));
			} else if (itemList.get(itemListCounter).getType().equals(ItemType.BEVERAGE)) {
				beverageSubOrder.setAmount(itemList.get(itemListCounter).getPrice()
						+ (beverageSubOrder.getAmount() != null ? beverageSubOrder.getAmount() : 0));
				beverageSubOrder.setQuantity(itemList.get(itemListCounter).getQuantity()
						+ (beverageSubOrder.getQuantity() != null ? beverageSubOrder.getQuantity() : 0));
				beverageSubOrder.setSubOrderType(ItemType.BEVERAGE);
				beverageSubOrder.addToItems(itemList.get(itemListCounter));
			} else if (itemList.get(itemListCounter).getType().equals(ItemType.SIDES)) {
				sidesSubOrder.setAmount(itemList.get(itemListCounter).getPrice()
						+ (sidesSubOrder.getAmount() != null ? sidesSubOrder.getAmount() : 0));
				sidesSubOrder.setQuantity(itemList.get(itemListCounter).getQuantity()
						+ (sidesSubOrder.getQuantity() != null ? sidesSubOrder.getQuantity() : 0));
				sidesSubOrder.setSubOrderType(ItemType.SIDES);
				sidesSubOrder.addToItems(itemList.get(itemListCounter));
			}
			itemListCounter++;
			iteratorItemList.next();
		}


		order.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		order.setUser(user);
		order.setTotalAmount(totalPrice);
		order.setStatus(Status.SUBMITTED);
		order.setSubOrders(subOrderList);
		order.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		order.setPaymentType(PaymentType.NOTSELECTED);

		
		order.addToSubOrders(sidesSubOrder);
		order.addToSubOrders(beverageSubOrder);
		order.addToSubOrders(pizzaSubOrder);
		
		System.out.println("aaaaaaaaaa   "+sidesSubOrder.getItems());
		System.out.println("aaaaaaaaaa   "+beverageSubOrder.getItems());
		System.out.println("aaaaaaaaaa   "+pizzaSubOrder.getItems());
		
		Order finalOrder = orderDAO.generateOrder(order);

		return finalOrder;
	}

	public User getUserDetails(Principal pricipalUserObject) {

		LOG.info("Fetching Details for " + pricipalUserObject.getName() + " from the database");
		User user = orderDAO.getUserDetails(pricipalUserObject.getName());
		return user;
	}
}
