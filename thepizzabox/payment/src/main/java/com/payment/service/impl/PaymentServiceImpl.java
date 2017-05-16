package com.payment.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.payment.dao.PaymentDAO;
import com.payment.data.PaymentDetails;
import com.payment.exception.DAOException;
import com.payment.exception.ErrorCode;
import com.payment.exception.PaymentServiceException;
import com.payment.service.PaymentService;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;

/**
 * PaymentServiceImpl contains the implementation of PaymentService APIs
 * 
 * @author rupalip
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentDAO paymentDAO;

	/**
	 * This API carries out the actual processing of the payment
	 *  
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @throws PaymentServiceException 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void executePayment(final PaymentDetails paymentDetails) throws PaymentServiceException {
		final Order order = paymentDetails.getOrder();
		final Double totalAmount = order.getTotalAmount();
		
		LOG.info("Initiating payment execution for order ID["+order.getId()+"]");
		
		final Double balance = getBalance(paymentDetails);
		deductAmountFromBalance(balance, totalAmount, paymentDetails);
		updateBalance(paymentDetails);
		
		LOG.info("Payment execution is complete for order ID["+order.getId()+"]");
	}

	/**
	 * This API updates the order status with the given status parameter
	 * 
	 * @param orderId
	 * 			ID of the order to be updated
	 * @param status
	 * 			Order status to update with
	 * @throws PaymentServiceException 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateOrderStatus(final Integer orderId, final Status status) throws PaymentServiceException {
		LOG.info("Updating order status to "+status.getStatus()+" for order ID["+orderId+"]");
		
		try {
			paymentDAO.updateOrderStatus(orderId, status);
		} 
		catch (DAOException e) {
			LOG.error("Error occurred while updating order status for order ID["+orderId+"]");
			throw new PaymentServiceException(e);
		}
	}
	
	/**
	 * Gets the balance for a card from the database
	 * 
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @return balance
	 * 			Balance in the card
	 * @throws PaymentServiceException
	 */
	private Double getBalance(final PaymentDetails paymentDetails) throws PaymentServiceException{
		Double balance = null;
		final CardDetails cardDetails = paymentDetails.getCardDetails();
		
		try {
			balance = paymentDAO.getBalanceForCard(cardDetails);
		} 
		catch (DAOException e) {
			setErrorCodeAndThrowException("Error occurred while obtaining balance for card["+cardDetails.getCardNumber()+"]", ErrorCode.BALANCE_UNAVAILABLE, e, paymentDetails);
		} 
		return balance;
	}
	
	/**
	 * Deducts the total amount from the balance
	 * 
	 * @param balance
	 * 			Balance to be updated after total amount deduction
	 * @param totalAmount
	 * 			Total amount of the order
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @throws PaymentServiceException
	 */
	private void deductAmountFromBalance(Double balance, final Double totalAmount, final PaymentDetails paymentDetails) throws PaymentServiceException{
		if (balance == null || balance < totalAmount) {
			setErrorCodeAndThrowException("Insufficient balance in card["+paymentDetails.getCardDetails().getCardNumber()+"]", 
						ErrorCode.INSUFFICIENT_BALANCE, paymentDetails);
		}
		
		balance -= totalAmount;
		paymentDetails.getCardDetails().setBalance(balance);
	}

	/**
	 * Updates the updated balance in the database
	 * 
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @param balance
	 * 			Balance to be updated after total amount deduction
	 * @throws PaymentServiceException
	 */
	private void updateBalance(final PaymentDetails paymentDetails) throws PaymentServiceException{
		final CardDetails cardDetails = paymentDetails.getCardDetails();
		final String cardNumber = cardDetails.getCardNumber();
		
		LOG.info("Updating balance for card["+cardNumber+"]");
		try {
			paymentDAO.updateBalance(cardDetails);
		} 
		catch (DAOException e) {
			setErrorCodeAndThrowException("Error occurred while updating balance for card["+cardNumber+"]", ErrorCode.BALANCE_UPDATE, e, paymentDetails);
		}
		
	}
	
	/**
	 * Sets error code in payment result and throws exception
	 * 
	 * @param errMsg
	 * 			Error message to be logged and thrown
	 * @param errorCode
	 * 			Error code for the message
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @throws PaymentServiceException
	 */
	private void setErrorCodeAndThrowException(final String errMsg, final ErrorCode errorCode, final PaymentDetails paymentDetails) throws PaymentServiceException{
		LOG.error(errMsg);
		paymentDetails.getPaymentResult().setErrorCode(errorCode);
		throw new PaymentServiceException(errMsg);
	}
	
	/**
	 * Sets error code in payment result and throws exception
	 * 
	 * @param errMsg
	 * 			Error message to be logged and thrown
	 * @param errorCode
	 * 			Error code for the message
	 * @param e
	 * 			Exception object 
	 * @param paymentDetails
	 * 			PaymentDetails containing order, user and card details
	 * @throws PaymentServiceException
	 */
	private void setErrorCodeAndThrowException(final String errMsg, final ErrorCode errorCode, final Throwable e, final PaymentDetails paymentDetails) throws PaymentServiceException{
		LOG.error(errMsg);
		paymentDetails.getPaymentResult().setErrorCode(errorCode);
		throw new PaymentServiceException(errorCode.getDescription(), e);
	}
}
