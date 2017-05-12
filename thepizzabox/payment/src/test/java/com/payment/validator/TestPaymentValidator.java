package com.payment.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.common.JUnitConstants;
import com.payment.common.JUnitTestHelper;
import com.payment.data.PaymentDetails;
import com.payment.exception.PaymentProcessException;
import com.payment.exception.PaymentValidationException;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.ValidationErrorMessage;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestPaymentValidator {

	@Autowired
	private PaymentValidator paymentValidator;
	
	@Test
	public void testValidateOrder() throws PaymentValidationException{
		Order order = createOrder(100.0, null);
		paymentValidator.validate(order);
	}
	
	@Test(expected=PaymentValidationException.class)
	public void testValidateNullOrder() throws PaymentValidationException{
		Order order = null;
		paymentValidator.validate(order);
	}
	
	@Test(expected=PaymentValidationException.class)
	public void testValidateNullOrderId() throws PaymentValidationException{
		paymentValidator.validate(new Order());
	}
	
	@Test(expected=PaymentValidationException.class)
	public void testValidateOrderNullTotalAmount() throws PaymentValidationException{
		Order order = createOrder(null, null);
		paymentValidator.validate(order);
	}
	
	@Test(expected=PaymentValidationException.class)
	public void testValidateOrderZeroTotalAmount() throws PaymentValidationException{
		Order order = createOrder(0.0, null);
		paymentValidator.validate(order);
	}
	
	@Test
	public void testValidateUser() throws PaymentValidationException{
		paymentValidator.validate(JUnitTestHelper.createUser());
	}
	
	@Test(expected=PaymentValidationException.class)
	public void testValidateNullUser() throws PaymentValidationException{
		User user = null;
		paymentValidator.validate(user);
	}
	
	@Test(expected=PaymentValidationException.class)
	public void testValidateNullUserID() throws PaymentValidationException{
		paymentValidator.validate(new User());
	}
	
	@Test
	public void testValidateOrderAndUser() throws PaymentValidationException{
		Order order = createOrder(100.0, null);
		paymentValidator.validate(order, JUnitTestHelper.createUser());
	}
	
	@Test
	public void testValidateOrderAndUserAndCardDetails() throws PaymentValidationException{
		Order order = createOrder(100.0, PaymentType.CASH);
		CardDetails cardDetails = new CardDetails(JUnitTestHelper.createUser(), JUnitConstants.CARD_NUMBER, JUnitConstants.EXPIRY_DATE, JUnitConstants.CVV);
		String result = paymentValidator.validate(order, JUnitTestHelper.createUser(), cardDetails);
		Assert.assertEquals(Constants.NO_ERROR, result);
	}
	
	@Test(expected=PaymentValidationException.class)
	public void testValidateOrderAndUserAndCardDetailsForNullPaymentType() throws PaymentValidationException{
		Order order = createOrder(100.0, null);
		CardDetails cardDetails = new CardDetails(JUnitTestHelper.createUser(), JUnitConstants.CARD_NUMBER, JUnitConstants.EXPIRY_DATE, JUnitConstants.CVV);
		String result = paymentValidator.validate(order, JUnitTestHelper.createUser(), cardDetails);
		Assert.assertEquals(Constants.NO_ERROR, result);
	}
	
	@Test(expected=PaymentValidationException.class)
	public void testValidateOrderAndUserAndCardDetailsNotSelectedPaymentType() throws PaymentValidationException{
		Order order = createOrder(100.0, PaymentType.NOTSELECTED);
		CardDetails cardDetails = new CardDetails(JUnitTestHelper.createUser(), JUnitConstants.CARD_NUMBER, JUnitConstants.EXPIRY_DATE, JUnitConstants.CVV);
		String result = paymentValidator.validate(order, JUnitTestHelper.createUser(), cardDetails);
		Assert.assertEquals(Constants.NO_ERROR, result);
	}
	
	@Test
	public void testValidateOrderAndUserAndCardDetailsError() throws PaymentValidationException{
		Order order = createOrder(100.0, PaymentType.ONLINE);
		CardDetails cardDetails = new CardDetails(JUnitTestHelper.createUser(), JUnitConstants.CARD_NUMBER, JUnitConstants.EXPIRY_DATE, null);
		String result = paymentValidator.validate(order, JUnitTestHelper.createUser(), cardDetails);
		Assert.assertEquals(ValidationErrorMessage.CVV_BLANK, result);
	}
	
	@Test
	public void testValidatePaymentDetails() throws PaymentProcessException{
		PaymentDetails paymentDetails = new PaymentDetails();
		CardDetails cardDetails = new CardDetails();
		cardDetails.setUser(new User());
		paymentDetails.setCardDetails(cardDetails);
		paymentDetails.setOrder(new Order());
		paymentValidator.validate(paymentDetails);
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testValidatePaymentDetailsNullCardDetails() throws PaymentProcessException{
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setOrder(new Order());
		paymentValidator.validate(paymentDetails);
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testValidatePaymentDetailsNullUser() throws PaymentProcessException{
		PaymentDetails paymentDetails = new PaymentDetails();
		CardDetails cardDetails = new CardDetails();
		paymentDetails.setCardDetails(cardDetails);
		paymentDetails.setOrder(new Order());
		paymentValidator.validate(paymentDetails);
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testValidatePaymentDetailsNullOrder() throws PaymentProcessException{
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setOrder(new Order());
		paymentValidator.validate(paymentDetails);
	}
	
	private Order createOrder(Double totalAmount, PaymentType paymentType){
		Order order = new Order();
		order.setId(1);
		order.setPaymentType(paymentType);
		order.setTotalAmount(totalAmount);
		
		return order;
	}
}
