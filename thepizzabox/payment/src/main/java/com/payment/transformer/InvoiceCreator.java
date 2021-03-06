package com.payment.transformer;

import java.sql.Timestamp;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.payment.data.Invoice;
import com.payment.data.PaymentDetails;
import com.payment.data.PaymentResultStatus;
import com.payment.exception.PaymentProcessException;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;

/**
 * This class is responsible for creating invoice represnting the order and
 * payment details and result
 * 
 * @author rupalip
 *
 */
@Component
public class InvoiceCreator {

	private static final Logger LOG = Logger.getLogger(InvoiceCreator.class);

	/**
	 * This method creates invoice with payment details
	 * 
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @return invoice
	 * 			Invoice with order details and payment status
	 * @throws PaymentProcessException
	 */
	public Invoice create(final PaymentDetails paymentDetails) throws PaymentProcessException {

		if (paymentDetails == null || paymentDetails.getOrder() == null) {
			String errMsg = "Cannot create invoice as payment or order details are not available";
			LOG.error(errMsg);
			throw new PaymentProcessException(errMsg);
		}

		final Invoice invoice = createInvoice(paymentDetails);
		LOG.info("Invoice created - " + invoice);
		return invoice;
	}

	/**
	 * This method creates invoice with payment details
	 * 
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @return invoice
	 * 			Invoice with order details and payment status
	 */
	private Invoice createInvoice(final PaymentDetails paymentDetails) {
		final Order order = paymentDetails.getOrder();
		final CardDetails cardDetails = paymentDetails.getCardDetails();
		
		final Invoice invoice = new Invoice();

		LOG.info("Creating invoice for order ID[" + order.getId() + "] and user[" + cardDetails.getUser().getUsername()
				+ "]");

		final Random random = new Random(System.currentTimeMillis());
		final int id = ((1 + random.nextInt(2)) * 10000 + random.nextInt(10000));

		invoice.setId(Constants.INV + id);
		invoice.setOrderId(order.getId());
		invoice.setAmount(order.getTotalAmount());
		invoice.setInvoiceTimestamp(new Timestamp(System.currentTimeMillis()));
		invoice.setPaymentType(order.getPaymentType());
		invoice.setUsername(cardDetails.getUser().getUsername());
		
		setTransactionStatus(invoice, paymentDetails);
		
		return invoice;
	}

	/**
	 * This method sets transaction status in the invoice
	 * If payment type is cash, transaction status is success
	 * For online payment type the paymentResultStatus is populated by the
	 * PaymentProcessor
	 * 
	 * @param invoice
	 * 			Invoice with order details and payment status
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 */
	private void setTransactionStatus(final Invoice invoice, final PaymentDetails paymentDetails) {
		final PaymentResultStatus paymentResultStatus = paymentDetails.getPaymentResult().getPaymentResultStatus();
		
		// setting transaction status for cash payment mode
		// paymentResultStatus will be null for CASH type payment as we do
		// not carry out any payment processing
		if (paymentResultStatus == null && paymentDetails.getOrder().getPaymentType() == PaymentType.CASH) {
			invoice.setTransactionStatus(PaymentResultStatus.SUCCESS.toString());
		}
		// setting transaction status for online payment mode
		else {
			invoice.setTransactionStatus(paymentResultStatus.toString());
		}
	}
}
