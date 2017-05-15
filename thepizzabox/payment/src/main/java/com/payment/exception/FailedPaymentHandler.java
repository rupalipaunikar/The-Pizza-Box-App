package com.payment.exception;

import java.sql.Timestamp;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.message.ErrorMessage;
import org.springframework.stereotype.Component;

import com.payment.data.Invoice;
import com.payment.data.PaymentDetails;
import com.payment.data.PaymentResultStatus;
import com.payment.service.PaymentService;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;

/**
 * This class handles error occurred during messaging
 * flow
 * 
 * @author rupalip
 *
 */
@Component
public class FailedPaymentHandler {

	private static final Logger LOG = Logger.getLogger(FailedPaymentHandler.class);

	@Autowired
	private PaymentService paymentService;

	/**
	 * Handles error occurred during messaging
	 * flow and sets transaction status to FAILED
	 * 
	 * @param errorMessage
	 * 			exception with failed message
	 * @return invoice
	 * 			Invoice with order and error details 
	 */
	public Invoice handleError(final ErrorMessage errorMessage) {
		final MessageHandlingException exception = (MessageHandlingException) errorMessage.getPayload();
		final PaymentDetails paymentDetails = (PaymentDetails) exception.getFailedMessage().getPayload();
		
		final Invoice invoice = createErrorInvoice(paymentDetails);
		setTransactionStatus(invoice, paymentDetails.getPaymentResult().getErrorCode());
		
		final Integer orderID = paymentDetails.getOrder().getId();
		try {
			paymentService.updateOrderStatus(orderID, Status.FAILED);
		} 
		catch (PaymentServiceException e) {
			LOG.error("Error occurred while updating order to FAILED status for order ID["+orderID+"]");
		}
		
		return invoice;
	}
	
	/**
	 * Creates error invoice for error scenarios
	 * 
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @return invoice
	 * 			Invoice to be populated with error details
	 */
	private Invoice createErrorInvoice(final PaymentDetails paymentDetails){
		final Order order = paymentDetails.getOrder();
		final Invoice invoice = new Invoice();
		
		final Random random = new Random(System.currentTimeMillis());
		final int id = ((1 + random.nextInt(2)) * 10000 + random.nextInt(10000));

		invoice.setId(Constants.INV + id);
		invoice.setOrderId(order.getId());
		invoice.setAmount(order.getTotalAmount());
		invoice.setInvoiceTimestamp(new Timestamp(System.currentTimeMillis()));
		invoice.setPaymentType(order.getPaymentType());
		invoice.setUsername(paymentDetails.getCardDetails().getUser().getUsername());
		return invoice;
	}
	
	/**
	 * Sets transaction status in invoice
	 * 
	 * @param invoice
	 * 			Invoice to be populated with transaction status
	 * @param errorCode
	 * 			ErrorCode pertaining to the error occurred
	 */
	private void setTransactionStatus(final Invoice invoice, final ErrorCode errorCode){
		final String msg = PaymentResultStatus.FAILED.toString() + Constants.HYPHEN;
		
		if(errorCode == null){
			//setting error code for scenarios where exception was thrown from payment processor 
			// itself and not from the service layer
			invoice.setTransactionStatus(msg + ErrorCode.PAYMENT_PROCESS.getDescription());
		}
		else{
			invoice.setTransactionStatus(msg + errorCode.getDescription());
		}
	}
}
