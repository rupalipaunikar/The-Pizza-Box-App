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
	 * 			Order being processed
	 * @return channel
	 * 			Destination channel selected on the basis of payment type
	 * @throws PaymentProcessException 
	 */
	public String route(final PaymentDetails paymentDetails) throws PaymentProcessException{
		final PaymentType paymentType = paymentDetails.getOrder().getPaymentType();
		final Integer id = paymentDetails.getOrder().getId();
		
		LOG.info("Validating payment type for order ID["+id+"]");
		validate(paymentType);
		
		final String destinationChannel = ((paymentType == PaymentType.CASH)? Constants.CASH_CHANNEL : Constants.ONLINE_CHANNEL);
		
		LOG.info("Routing order ID["+id+"] to ["+destinationChannel+"] channel");
		return destinationChannel;
	}
	
	/**
	 * Validates payment type
	 * 
	 * @param paymentType
	 * 			ONLINE | CASH
	 * @throws PaymentProcessException
	 */
	private void validate(final PaymentType paymentType) throws PaymentProcessException{
		if(paymentType == null){
			final String errMsg = "Could not route the order as payment type is not available";
			LOG.error(errMsg);
			throw new PaymentProcessException(errMsg);
		}
		
		if(!(paymentType == PaymentType.CASH || paymentType == PaymentType.ONLINE)){
			final String errMsg = "Could not route the order as payment type["+paymentType.toString()+"] is invalid";
			LOG.error(errMsg);
			throw new PaymentProcessException(errMsg);
		}
	}
}
