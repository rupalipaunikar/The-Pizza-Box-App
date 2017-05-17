package com.pizzaboxcore.splitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.pizzabox.common.model.Order;

/**
 * 
 * @author Roshni
 */

public class OrderSplitter {
	
	public Object splitOrderList(Message<?> message){
		
		Collection<Message<?>> messages = new ArrayList<Message<?>>();
		
		List<Order> orderList = (List<Order>) message.getPayload();
		int orderListSize = orderList.size();
		
		Order order = null;
		for(int counter=0; counter<orderListSize ; counter++){
			order= orderList.get(counter);
			Message<?> msg = MessageBuilder.withPayload(order).build();
			messages.add(msg);
		}
		return messages;
	}

}
