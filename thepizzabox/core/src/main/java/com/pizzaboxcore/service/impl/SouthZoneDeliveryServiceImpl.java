package com.pizzaboxcore.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;
import com.pizzaboxcore.custom.exception.DAOException;
import com.pizzaboxcore.dao.OrderDAO;
import com.pizzaboxcore.delivery.DeliveryPerson;
import com.pizzaboxcore.service.DeliveryService;

@Service
public class SouthZoneDeliveryServiceImpl extends DeliveryService {

	private static final Logger LOG = Logger.getLogger(SouthZoneDeliveryServiceImpl.class);

	private DeliveryPerson deliveryPerson;
	@Autowired
	private OrderDAO orderDAO;
	
	@Override
	public void handleOrderDelivery(Order order) {
		LOG.info("Initiating order delivery...");
		deliveryPerson = chooseDeliveryPerson();
		deliveryPerson.deliverOrder(order);
		LOG.info("Order delivery completed...");
		
		try {
			orderDAO.updateOrderStatus(order.getId(), Status.DELIVERED);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
