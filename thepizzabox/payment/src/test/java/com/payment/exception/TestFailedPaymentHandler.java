package com.payment.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.message.ErrorMessage;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.common.JUnitConstants;
import com.payment.common.JUnitTestHelper;
import com.payment.data.Invoice;
import com.payment.data.PaymentDetails;
import com.payment.data.PaymentResult;
import com.payment.data.PaymentResultStatus;
import com.payment.service.PaymentService;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestFailedPaymentHandler {

	@InjectMocks
	private FailedPaymentHandler failedPaymentHandler;
	
	@Mock
	private PaymentService paymentService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testHandleErrorWithErrorCode() throws PaymentServiceException{
		Invoice invoice = failedPaymentHandler.handleError(createErrorMessage(ErrorCode.BALANCE_UNAVAILABLE));
		assertInvoice(invoice, ErrorCode.BALANCE_UNAVAILABLE);
	}
	
	@Test
	public void testHandleErrorWithoutErrorCode() throws PaymentServiceException{
		Invoice invoice = failedPaymentHandler.handleError(createErrorMessage(null));
		assertInvoice(invoice, ErrorCode.PAYMENT_PROCESS);
		
	}
	
	private ErrorMessage createErrorMessage(ErrorCode errorCode) throws PaymentServiceException{
		Order order = new Order(1, PaymentType.ONLINE, null, null, 1000.0, null, null, null);
		PaymentResult paymentResult = new PaymentResult(null, errorCode);
		PaymentDetails paymentDetails = new PaymentDetails(JUnitTestHelper.createCardDetails(JUnitConstants.CARD_NUMBER, JUnitTestHelper.createUser()), order, paymentResult);
		
		Message<PaymentDetails> failedMessage = new GenericMessage<PaymentDetails>(paymentDetails);
		MessageHandlingException messageException = new MessageHandlingException(failedMessage);
		
		Mockito.doNothing().when(paymentService).updateOrderStatus(Mockito.anyInt(), Mockito.any(Status.class));
		return new ErrorMessage(messageException);
	}
	
	private void assertInvoice(Invoice invoice, ErrorCode errorCode){
		Assert.assertNotNull(invoice);
		Assert.assertTrue(1 == invoice.getOrderId());
		Assert.assertTrue(1000.0 == invoice.getAmount());
		Assert.assertEquals(PaymentType.ONLINE, invoice.getPaymentType());
		Assert.assertEquals(Constants.USER, invoice.getUsername());
		Assert.assertEquals(PaymentResultStatus.FAILED.toString() + Constants.HYPHEN + errorCode.getDescription(), invoice.getTransactionStatus());
	}
}
