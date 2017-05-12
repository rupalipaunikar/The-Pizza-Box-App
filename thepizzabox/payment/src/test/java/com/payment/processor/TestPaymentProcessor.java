package com.payment.processor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.common.JUnitConstants;
import com.payment.data.PaymentDetails;
import com.payment.data.PaymentResult;
import com.payment.data.PaymentResultStatus;
import com.payment.exception.ErrorCode;
import com.payment.exception.PaymentProcessException;
import com.payment.exception.PaymentServiceException;
import com.payment.service.PaymentService;
import com.payment.validator.PaymentValidator;
import com.payment.validator.ValidationUtils;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestPaymentProcessor {

	@Mock
	private PaymentService paymentService;
	
	@Mock
	private ValidationUtils validationUtils;
	
	@Mock
	private PaymentValidator paymentValidator;
	
	@InjectMocks
	private PaymentProcessor paymentProcessor;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testProcessPayment() throws Exception{
		Mockito.when(validationUtils.getPaymentValidator()).thenReturn(paymentValidator);
		Mockito.doNothing().when(paymentValidator).validate(Mockito.any(PaymentDetails.class));
		
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setOrder(new Order());
		paymentDetails.setPaymentResult(new PaymentResult());
		
		Mockito.doNothing().when(paymentService).executePayment(paymentDetails);
		Mockito.doNothing().when(paymentService).updateOrderStatus(Mockito.anyInt(), Mockito.any(Status.class));
		
		paymentProcessor.processPayment(paymentDetails);
		
		Assert.assertEquals(PaymentResultStatus.SUCCESS, paymentDetails.getPaymentResult().getPaymentResultStatus());
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testProcessPaymentWithPaymentServiceException() throws Exception{
		Mockito.when(validationUtils.getPaymentValidator()).thenReturn(paymentValidator);
		Mockito.doNothing().when(paymentValidator).validate(Mockito.any(PaymentDetails.class));
		
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setOrder(new Order());
		paymentDetails.setPaymentResult(new PaymentResult());
		
		Mockito.doThrow(PaymentServiceException.class).when(paymentService).executePayment(paymentDetails);
		paymentProcessor.processPayment(paymentDetails);
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testProcessPaymentWithException() throws Exception{
		Mockito.when(validationUtils.getPaymentValidator()).thenReturn(paymentValidator);
		Mockito.doNothing().when(paymentValidator).validate(Mockito.any(PaymentDetails.class));
		
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setOrder(new Order());
		paymentDetails.setPaymentResult(new PaymentResult());
		
		Mockito.doThrow(Exception.class).when(paymentService).executePayment(paymentDetails);
		paymentProcessor.processPayment(paymentDetails);
		
		Assert.assertEquals(ErrorCode.PAYMENT_PROCESS, paymentDetails.getPaymentResult().getErrorCode());
	}
	
}
