package com.pizzaboxcore.validator;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzaboxcore.constants.Constants;
import com.pizzaboxcore.custom.exception.CustomGenericException;
import com.pizzaboxcore.service.impl.OrderServiceImpl;

@Component
public class OrderValidator {

private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class);
	
	public void validateGeneratedOrder(Order order){
		if(order==null || order.getId()==null){
			
			LOG.info(Constants.ORDER_GENERATED_NULL);
			throw new CustomGenericException(Constants.ORDER_GENERATED_NULL);
		}
	}
	
	public void validateGeneratedOrderList(List<Item> finalOrderList){
		if(finalOrderList==null){
			LOG.info(Constants.ORDERLIST_GENERATED_NULL);
			throw new CustomGenericException(Constants.ORDERLIST_GENERATED_NULL);
		}
	}
}
