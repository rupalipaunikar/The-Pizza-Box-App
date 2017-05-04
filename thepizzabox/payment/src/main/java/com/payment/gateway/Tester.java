package com.payment.gateway;

import com.pizzabox.common.model.Order;

public class Tester {

	public void display(Order o){
		System.out.println("I am in Tester "+ o.toString()+"  \nUser " +o.getUser());
	}
}
