package com.payment.transformer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.common.JUnitConstants;
import com.payment.common.JUnitTestHelper;
import com.payment.data.Invoice;
import com.payment.data.PaymentDetails;
import com.payment.data.PaymentResult;
import com.payment.data.PaymentResultStatus;
import com.payment.exception.PaymentProcessException;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestInvoiceCreator {

	@Autowired
	private InvoiceCreator invoiceCreator;
	
	@Test
	public void testCreateInvoice() throws PaymentProcessException{
		PaymentDetails paymentDetails = createPaymentDetails(PaymentType.CASH, false);
		Invoice invoice = invoiceCreator.create(paymentDetails);
		assertInvoice(invoice, PaymentType.CASH);
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testCreateInvoiceWithNullPaymentDetails() throws PaymentProcessException{
		 invoiceCreator.create(null);
	}
	
	@Test(expected=PaymentProcessException.class)
	public void testCreateInvoiceWithNullOrder() throws PaymentProcessException{
		 invoiceCreator.create(new PaymentDetails());
	}
	
	@Test
	public void testCreateInvoiceWithNullPaymentResultStatus() throws PaymentProcessException{
		PaymentDetails paymentDetails = createPaymentDetails(PaymentType.ONLINE, true);
		Invoice invoice = invoiceCreator.create(paymentDetails);
		assertInvoice(invoice, PaymentType.ONLINE);
	}
	
	private PaymentDetails createPaymentDetails(PaymentType paymentType, boolean createPaymentResultStatus){
		PaymentDetails paymentDetails = new PaymentDetails();
		CardDetails cardDetails = JUnitTestHelper.createCardDetails(JUnitConstants.CARD_NUMBER, JUnitTestHelper.createUser());
		Order order = new Order(1, paymentType, null, null, 1000.0, null, null, null);
		paymentDetails.setCardDetails(cardDetails);
		paymentDetails.setOrder(order);
		
		PaymentResult paymentResult = new PaymentResult();
		
		if(createPaymentResultStatus){
			paymentResult.setPaymentResultStatus(PaymentResultStatus.SUCCESS);
		}
		paymentDetails.setPaymentResult(paymentResult);
		
		return paymentDetails;
	}
	
	private void assertInvoice(Invoice invoice, PaymentType paymentType){
		Assert.assertNotNull(invoice);
		Assert.assertNotNull(invoice.getId());
		Assert.assertNotNull(invoice.getInvoiceTimestamp());
		Assert.assertTrue(1000.0 == invoice.getAmount());
		Assert.assertTrue(1 == invoice.getOrderId());
		Assert.assertEquals(Constants.USER, invoice.getUsername());
		Assert.assertTrue(invoice.getId().contains(Constants.INV));
		Assert.assertEquals(paymentType, invoice.getPaymentType());
		Assert.assertEquals(PaymentResultStatus.SUCCESS.toString(), invoice.getTransactionStatus());
	}
}
