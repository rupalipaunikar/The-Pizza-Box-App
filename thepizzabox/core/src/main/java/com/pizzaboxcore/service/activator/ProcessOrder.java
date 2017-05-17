package com.pizzaboxcore.service.activator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.integration.Message;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.SubOrder;
import com.pizzabox.common.model.User;

/**
 * 
 * @author Roshni
 */
public class ProcessOrder {

	public List<Order> prepareOrder(Message<List<Map<String, Object>>> message) {

		System.out.println("###############################################" + message.getPayload());

		List<Order> orderList = new ArrayList<Order>();
		int order_id = 0, row_count = 1;
		Order order = null;
		List<Map<String, Object>> rows = message.getPayload();
		for (Map<String, Object> row : rows) {

			Integer orderID = (order != null ? order.getId() : new Integer(0));
			if (orderID != row.get("order_id")&&row_count>1) {
				orderList.add(order);
			}

			if (orderID == (int) row.get("order_id")) {
				order.getSubOrders().add(createSuborder(row));
			} else {

				List<SubOrder> suborderList = new ArrayList<SubOrder>();
				suborderList.add(createSuborder(row));

				order = new Order();
				order.setId((Integer) row.get("order_id"));
				order.setStatus(setStatus((Integer) row.get("status")));
				order.setCreatedTimestamp((Timestamp) row.get("created_timestamp"));
				order.setPaymentType(setPaymentType((Integer) row.get("payment_type")));
				order.setUser(createUser(row));
				order.setTotalAmount((Double) row.get("total_amount"));
				order.setSubOrders(suborderList);
				order.setUpdatedTimestamp((Timestamp) row.get("updated_timestamp"));

			}

			row_count++;
		}
		orderList.add(order);
		return orderList;
	}

	

	public User createUser(Map<String, Object> row) {
		User user = new User();
		user.setUserId((Integer) row.get("user_id"));
		user.setUsername((String) row.get("first_name"));
		user.setRole((String) row.get("last_name"));
		user.setPhoneNo((String) row.get("address"));
		user.setPassword((String) row.get("contact_no"));
		user.setLastName((String) row.get("username"));
		user.setFirstName((String) row.get("password"));
		user.setAddress((String) row.get("role"));

		return user;
	}

	public SubOrder createSuborder(Map<String, Object> row) {
		SubOrder subOrder = new SubOrder();
		subOrder.setId((Integer) row.get("suborder_id"));
		subOrder.setAmount((Double) row.get("amount"));
		subOrder.setQuantity((Integer) row.get("quantity"));
		System.out.println("TYPE $$$$$$$$$$$$$$$$$$$$$" + row.get("type"));
		subOrder.setSubOrderType(setItemType((Integer) row.get("type")));
		return subOrder;
	}

	public ItemType setItemType(Integer type) {
		ItemType itemType = null;
		if (type == 0)
			itemType = ItemType.PIZZA;
		else if (type == 1)
			itemType = ItemType.SIDES;
		else if (type == 2)
			itemType = ItemType.BEVERAGE;

		return itemType;
	}

	public Status setStatus(Integer status) {
		Status statusType = null;
		if (status == 1)
			statusType = Status.PAID_CASH;
		else if (status == 2)
			statusType = Status.PAID_ONLINE;
		return statusType;
	}

	public PaymentType setPaymentType(Integer paymentType) {
		PaymentType payment = null;
		if (paymentType == 0)
			payment = PaymentType.CASH;
		else if (paymentType == 1)
			payment = PaymentType.ONLINE;
		return payment;
	}
}
