package com.payment.router;

import org.apache.log4j.Logger;

import com.payment.data.PaymentDetails;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;

/**
 * This is a custom router used for routing the orders to cash or online 
 * channel based on their payment type
 * 
 * @author rupalip
 *
 */
public class PaymentModeRouter {

	private static final Logger LOG = Logger.getLogger(PaymentModeRouter.class);
	
	/**
	 * Routes order based on their payment type
	 * 
	 * @param order
	 * @return channel
	 */
	public String route(PaymentDetails paymentDetails){
		String destinationChannel = ((paymentDetails.getOrder().getPaymentType() == PaymentType.CASH)? Constants.CASH_CHANNEL : Constants.ONLINE_CHANNEL);
		
		LOG.info("Routing order ID["+paymentDetails.getOrder().getId()+"] to ["+destinationChannel+"] channel");
		return destinationChannel;
	}
}
