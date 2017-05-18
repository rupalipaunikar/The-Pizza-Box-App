package com.pizzaboxcore.service.activator;

import org.springframework.integration.Message;
/**
 * 
 * @author Roshni
 */
public class PreparePizza {

	public void preparePizza(Message<?> message){
		
		
		System.out.println("Preparing Pizza");
	}
}
