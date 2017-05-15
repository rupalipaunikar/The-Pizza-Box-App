package com.payment.router;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.payment.data.PaymentDetails;
import com.payment.exception.PaymentProcessException;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;

/**
 * This is a custom router used for routing the orders to cash or online 
 * channel based on their payment type
 * 
 * @author rupalip
 *
 */
@Component
public class PaymentModeRouter {

	private static final Logger LOG = Logger.getLogger(PaymentModeRouter.class);
	
	/**
	 * Routes order based on their payment type
	 * 
	 * @param order
	 * @return channel
	 * @throws PaymentProcessException 
	 */
	public String route(PaymentDetails paymentDetails) throws PaymentProcessException{
		PaymentType paymentType = paymentDetails.getOrder().getPaymentType();
		Integer id = paymentDetails.getOrder().getId();
		
		LOG.info("Validating payment type for order ID["+id+"]");
		validate(paymentType);
		
		String destinationChannel = ((paymentType == PaymentType.CASH)? Constants.CASH_CHANNEL : Constants.ONLINE_CHANNEL);
		
		LOG.info("Routing order ID["+id+"] to ["+destinationChannel+"] channel");
		return destinationChannel;
	}
	
	/**
	 * Validates payment type
	 * 
	 * @param paymentType
	 * @throws PaymentProcessException
	 */
	private void validate(PaymentType paymentType) throws PaymentProcessException{
		if(paymentType == null){
			String errMsg = "Could not route the order as payment type is not available";
			LOG.error(errMsg);
			throw new PaymentProcessException(errMsg);
		}
		
		if(!(paymentType == PaymentType.CASH || paymentType == PaymentType.ONLINE)){
			String errMsg = "Could not route the order as payment type["+paymentType.toString()+"] is invalid";
			LOG.error(errMsg);
			throw new PaymentProcessException(errMsg);
		}
	}
}
