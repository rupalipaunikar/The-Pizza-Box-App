package com.pizzaboxcore.service.activator;

import java.util.List;
import java.util.Map;

import org.springframework.integration.Message;

import com.pizzabox.common.model.Order;

public class ProcessOrder {

	public void prepareOrder(Message<List<Map<String, Object>>> message){
		
        List<Map<String, Object>> rows = message.getPayload();
        for (Map<String, Object> row : rows) {
        	Order order = new Order();
            order.setId((Integer)row.get("order_id"));
            String category = (String) row.get("created_timestamp");
            String author = (String) row.get("payment_type");
            System.out.println(name + "-" + category + "-" + author);
        }
	}
}
