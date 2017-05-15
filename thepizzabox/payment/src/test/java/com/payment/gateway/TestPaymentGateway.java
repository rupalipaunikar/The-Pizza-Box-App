package com.payment.gateway;

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
import com.payment.exception.ErrorCode;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestPaymentGateway {

	@Autowired
	private PaymentGateway paymentGateway;

	@Test
	public void testCashPayment() {
//		Double totalAmount = 100.0;
//		Order order = new Order(1, PaymentType.CASH, Status.SUBMITTED, null, totalAmount, JUnitTestHelper.createUser(),
//				null, null);
//		PaymentDetails paymentDetails = createPaymentDetails(order);
//
//		Invoice invoice = paymentGateway.processPayment(paymentDetails);
//		assertInvoice(1, invoice, PaymentType.CASH, totalAmount, false);

	}

	//@Test
	public void testOnlinePayment() {
		Double totalAmount = 100.0;
		Order order = new Order(2, PaymentType.ONLINE, Status.SUBMITTED, null, totalAmount,
				JUnitTestHelper.createUser(), null, null);

		PaymentDetails paymentDetails = createPaymentDetails(order);

		Invoice invoice = paymentGateway.processPayment(paymentDetails);
		assertInvoice(2, invoice, PaymentType.ONLINE, totalAmount, false);

	}

	//@Test
	public void testPaymentException() {
		Double totalAmount = 10000.0;
		Order order = new Order(3, PaymentType.ONLINE, Status.SUBMITTED, null, totalAmount,
				JUnitTestHelper.createUser(), null, null);
		PaymentDetails paymentDetails = createPaymentDetails(order);

		Invoice invoice = paymentGateway.processPayment(paymentDetails);
		assertInvoice(3, invoice, PaymentType.ONLINE, totalAmount, true);

	}

	private PaymentDetails createPaymentDetails(Order order) {
		PaymentDetails paymentDetails = new PaymentDetails();

		paymentDetails.setOrder(order);
		CardDetails cardDetails = new CardDetails(JUnitTestHelper.createUser(), JUnitConstants.CARD_NUMBER,
				JUnitConstants.EXPIRY_DATE, JUnitConstants.CVV);
		paymentDetails.setCardDetails(cardDetails);
		paymentDetails.setPaymentResult(new PaymentResult());
		return paymentDetails;
	}

	private void assertInvoice(int orderID, Invoice invoice, PaymentType paymentType, Double totalAmount,boolean isFailed) {
		Assert.assertNotNull(invoice);
		Assert.assertTrue(orderID == invoice.getOrderId());
		Assert.assertTrue(totalAmount == invoice.getAmount());
		Assert.assertEquals(paymentType, invoice.getPaymentType());
		Assert.assertEquals(Constants.USER, invoice.getUsername());

		if (isFailed) {
			Assert.assertEquals(PaymentResultStatus.FAILED.toString() + Constants.HYPHEN
					+ ErrorCode.INSUFFICIENT_BALANCE.getDescription(), invoice.getTransactionStatus());
		} else {
			Assert.assertEquals(PaymentResultStatus.SUCCESS.toString(), invoice.getTransactionStatus());
		}
	}
}
