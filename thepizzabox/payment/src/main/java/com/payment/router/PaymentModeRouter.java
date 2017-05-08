package com.payment.router;

import org.apache.log4j.Logger;

import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.model.Order;

/**
 * This is a custom router used for routing the orders to cash or online 
 * channel based on their payment type
 * 
 * @author rupalip
 *
 */
public class PaymentModeRouter {

	private static final Logger LOG = Logger.getLogger(PaymentModeRouter.class);
	
	public String route(Order order){
		String destinationChannel = ((order.getPaymentType() == PaymentType.CASH)? Constants.CASH_CHANNEL : Constants.ONLINE_CHANNEL);
		
		LOG.info("Routing order ID["+order.getId()+"] to ["+destinationChannel+"] channel");
		return destinationChannel;
	}
}
