package com.pizzaboxcore.service.activator;

import org.springframework.integration.Message;
/**
 * 
 * @author Roshni
 */

public class PrepareSides {

	public void prepareSides(Message<?> message){
		System.out.println("Preparing Sides");
	}
}
