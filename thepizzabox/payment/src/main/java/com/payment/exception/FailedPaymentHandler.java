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
	 * @author rupalip
	 * @throws PaymentServiceException 
	 *
	 */
	public Invoice handleError(ErrorMessage errorMessage) {
		MessageHandlingException exception = (MessageHandlingException) errorMessage.getPayload();
		PaymentDetails paymentDetails = (PaymentDetails) exception.getFailedMessage().getPayload();
		
		Invoice invoice = createErrorInvoice(paymentDetails);
		setTransactionStatus(invoice, paymentDetails.getPaymentResult().getErrorCode());
		
		Integer id = paymentDetails.getOrder().getId();
		try {
			paymentService.updateOrderStatus(id, Status.FAILED);
		} 
		catch (PaymentServiceException e) {
			LOG.error("Error occurred while updating order to FAILED status for order ID["+id+"]");
		}
		
		return invoice;
	}
	
	/**
	 * Creates error invoice for error scenarios
	 * 
	 * @param invoice
	 * @param order
	 * @param cardDetails
	 * @param user
	 */
	private Invoice createErrorInvoice(PaymentDetails paymentDetails){
		Order order = paymentDetails.getOrder();
		Invoice invoice = new Invoice();
		
		Random r = new Random(System.currentTimeMillis());
		int id = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));

		invoice.setId(Constants.INV + id);
		invoice.setOrderId(order.getId());
		invoice.setAmount(order.getTotalAmount());
		invoice.setInvoiceTimestamp(new Timestamp(System.currentTimeMillis()));
		invoice.setPaymentType(order.getPaymentType());
		invoice.setUsername(paymentDetails.getCardDetails().getUser().getUsername());
		return invoice;
	}
	
	/**
	 * Sets transaction status
	 * 
	 * @param invoice
	 * @param errorCode
	 */
	private void setTransactionStatus(Invoice invoice, ErrorCode errorCode){
		String msg = PaymentResultStatus.FAILED.toString() + Constants.HYPHEN;
		
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
