package com.pizzaboxcore.service.activator;

import org.springframework.integration.Message;
/**
 * 
 * @author Roshni
 */
public class PrepareBeverage {
	
	public void prepareBeverage(Message<?> message){
		System.out.println("Preparing Beverage");
	}

}
