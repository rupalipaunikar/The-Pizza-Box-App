package com.pizzaboxcore.delivery;

import org.apache.log4j.Logger;

import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;

public class DeliveryPerson {

	private static final Logger LOG = Logger.getLogger(DeliveryPerson.class);
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DeliveryPerson() {}
	
	public DeliveryPerson(String name) {
		super();
		this.name = name;
	}

	public void deliverOrder(final Order order){
		User user = order.getUser();
		LOG.info("Order ID["+order.getId()+"] delievered to user["+user.getUsername()+"] at address["+user.getAddress()+"] by delivery boy["+name+"]");
	}
}
