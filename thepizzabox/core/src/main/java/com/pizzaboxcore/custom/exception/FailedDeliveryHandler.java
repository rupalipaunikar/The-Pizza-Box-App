package com.pizzaboxcore.custom.exception;

import org.springframework.stereotype.Component;

import com.pizzabox.common.model.Order;

@Component
public class FailedDeliveryHandler {

	public void handleFailedDelivery(final Order order){
		System.out.println("Handling failed order ----"+order);
	}
}
