package com.payment.service.impl;

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
import com.payment.dao.PaymentDAO;
import com.payment.data.PaymentDetails;
import com.payment.data.PaymentResult;
import com.payment.exception.DAOException;
import com.payment.exception.ErrorCode;
import com.payment.exception.PaymentServiceException;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestPaymentServiceImpl {
	
	@Mock
	private PaymentDAO paymentDAO;

	@InjectMocks
	private PaymentServiceImpl paymentService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testUpdateOrderStatus() throws Exception{
		Mockito.doNothing().when(paymentDAO).updateOrderStatus(Mockito.anyInt(), Mockito.any(Status.class));
		paymentService.updateOrderStatus(1, Status.FAILED);
	}
	
	@Test(expected=PaymentServiceException.class)
	public void testUpdateOrderStatusException() throws Exception{
		Mockito.doThrow(DAOException.class).when(paymentDAO).updateOrderStatus(Mockito.anyInt(), Mockito.any(Status.class));
		paymentService.updateOrderStatus(1, Status.FAILED);
	}
	
	@Test
	public void testExecutePayment() throws Exception{
		PaymentDetails paymentDetails = createPaymentDetails();
		
		Mockito.when(paymentDAO.getBalanceForCard(Mockito.any(CardDetails.class))).thenReturn(2000.0);
		Mockito.doNothing().when(paymentDAO).updateBalance(Mockito.any(CardDetails.class));
		paymentService.executePayment(paymentDetails);
		
		Assert.assertTrue(1000.0 == paymentDetails.getCardDetails().getBalance());
	}
	
	@Test(expected=PaymentServiceException.class)
	public void testExecutePaymentInsufficientBalance() throws Exception{
		PaymentDetails paymentDetails = createPaymentDetails();
		
		Mockito.when(paymentDAO.getBalanceForCard(Mockito.any(CardDetails.class))).thenReturn(0.0);
		Mockito.doNothing().when(paymentDAO).updateBalance(Mockito.any(CardDetails.class));
		paymentService.executePayment(paymentDetails);
		
		Assert.assertEquals(ErrorCode.INSUFFICIENT_BALANCE, paymentDetails.getPaymentResult().getErrorCode());
	}
	
	@Test(expected=PaymentServiceException.class)
	public void testUpdateBalance() throws Exception{
		PaymentDetails paymentDetails = createPaymentDetails();
		
		Mockito.doThrow(DAOException.class).when(paymentDAO).updateBalance(Mockito.any(CardDetails.class));
		paymentService.executePayment(paymentDetails);
		
		Assert.assertEquals(ErrorCode.BALANCE_UPDATE, paymentDetails.getPaymentResult().getErrorCode());
	}
	
	@Test(expected=PaymentServiceException.class)
	public void testGetBalanceForCardException() throws Exception{
		PaymentDetails paymentDetails = createPaymentDetails();
		
		Mockito.doThrow(DAOException.class).when(paymentDAO).getBalanceForCard(Mockito.any(CardDetails.class));
		paymentService.executePayment(paymentDetails);
		
		Assert.assertEquals(ErrorCode.BALANCE_UNAVAILABLE, paymentDetails.getPaymentResult().getErrorCode());
	}
	
	private PaymentDetails createPaymentDetails(){
		PaymentDetails paymentDetails = new PaymentDetails();
		Order order = new Order();
		order.setTotalAmount(1000.0);
		paymentDetails.setOrder(order);
		CardDetails cardDetails = new CardDetails();
		paymentDetails.setCardDetails(cardDetails);
		PaymentResult paymentResult = new PaymentResult();
		paymentDetails.setPaymentResult(paymentResult);
		
		return paymentDetails;
	}
	
}
