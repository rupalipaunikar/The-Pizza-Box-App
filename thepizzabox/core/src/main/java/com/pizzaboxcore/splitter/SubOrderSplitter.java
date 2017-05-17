package com.pizzaboxcore.splitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.SubOrder;
import com.pizzaboxcore.model.CompleteOrder;

/**
 * 
 * @author Roshni
 */

public class SubOrderSplitter {

	public Object splitSubOrder(Message<?> message) {
		final Order order = (Order) message.getPayload();
		Collection<Message<?>> messages = new ArrayList<Message<?>>();
		
		List<SubOrder> subOrderList = order.getSubOrders();
		for(int counter=0 ; counter<subOrderList.size() ; counter++){
			CompleteOrder completeSuborder = createSuborder(order,subOrderList.get(counter));
			Message<?> msg = MessageBuilder.withPayload(completeSuborder).build();
			messages.add(msg);
		}
		
		return messages;
	}
	
	public CompleteOrder createSuborder(Order order, SubOrder subOrder){
		CompleteOrder completeSubOrder = new  CompleteOrder();
		completeSubOrder.setOrder_id(order.getId());
		completeSubOrder.setCreatedTimestamp(order.getCreatedTimestamp());
		completeSubOrder.setTotalAmount(order.getTotalAmount());
		completeSubOrder.setPaymentType(order.getPaymentType());
		completeSubOrder.setStatus(order.getStatus());
		completeSubOrder.setUpdatedTimestamp(order.getUpdatedTimestamp());
		completeSubOrder.setUser(order.getUser());
		
		completeSubOrder.setSuborder_id(subOrder.getId());
		completeSubOrder.setAmount(subOrder.getAmount());
		completeSubOrder.setQuantity(subOrder.getQuantity());
		completeSubOrder.setSubOrderType(subOrder.getSubOrderType());
		
		return completeSubOrder;
	}
}
