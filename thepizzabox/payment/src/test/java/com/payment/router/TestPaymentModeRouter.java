package com.payment.router;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.common.JUnitConstants;
import com.payment.data.PaymentDetails;
import com.payment.exception.PaymentProcessException;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.model.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestPaymentModeRouter {

	@Autowired
	private PaymentModeRouter paymentModeRouter;
	
	@Test
	public void testRouteCashChannel() throws PaymentProcessException{
		PaymentDetails paymentDetails = createData(PaymentType.CASH);
		
		String channel = paymentModeRouter.route(paymentDetails);
		Assert.assertEquals(Constants.CASH_CHANNEL, channel);
	}
	
	@Test
	public void testRouteOnlineChannel() throws PaymentProcessException{
		PaymentDetails paymentDetails = createData(PaymentType.ONLINE);
		
		String channel = paymentModeRouter.route(paymentDetails);
		Assert.assertEquals(Constants.ONLINE_CHANNEL, channel);
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testWithNullPaymentType() throws PaymentProcessException{
		PaymentDetails paymentDetails = createData(null);
		paymentModeRouter.route(paymentDetails);
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testWithInvalidPaymentType() throws PaymentProcessException{
		PaymentDetails paymentDetails = createData(PaymentType.NOTSELECTED);
		paymentModeRouter.route(paymentDetails);
	}
	
	private PaymentDetails createData(PaymentType paymentType){
		PaymentDetails paymentDetails = new PaymentDetails();
		Order order = new Order();
		order.setPaymentType(paymentType);
		paymentDetails.setOrder(order);
		
		return paymentDetails;
	}
}
