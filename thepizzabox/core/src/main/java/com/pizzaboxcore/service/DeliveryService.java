package com.pizzaboxcore.service;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.pizzabox.common.model.Order;
import com.pizzaboxcore.delivery.DeliveryPerson;

public abstract class DeliveryService {

	protected DeliveryPerson deliveryPerson;
	
	private List<String> deliveryPersonList = Lists.newArrayList("Mr ABC ", "Mr XYZ", "Mr PQR", "Mr UVW");
	
	protected DeliveryPerson chooseDeliveryPerson(){
		Random random = new Random();
		
	    int randomIndex = random.nextInt(deliveryPersonList.size());
	    String randomElement = deliveryPersonList.get(randomIndex);
	    deliveryPerson = new DeliveryPerson(randomElement);
	    deliveryPersonList.remove(randomIndex);
		return deliveryPerson;
	}
	
	public abstract void handleOrderDelivery(final Order order);
}
