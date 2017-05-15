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

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void executePayment(PaymentDetails paymentDetails) throws PaymentServiceException {
		Order order = paymentDetails.getOrder();
		Double totalAmount = order.getTotalAmount();
		
		LOG.info("Initiating payment execution for order ID["+order.getId()+"]");
		
		Double balance = getBalance(paymentDetails);
		deductAmountFromBalance(balance, totalAmount, paymentDetails);
		updateBalance(paymentDetails);
		
		LOG.info("Payment execution is complete for order ID["+order.getId()+"]");
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateOrderStatus(Integer orderId, Status status) throws PaymentServiceException {
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
	 * @return
	 * @throws PaymentServiceException
	 */
	private Double getBalance(PaymentDetails paymentDetails) throws PaymentServiceException{
		Double balance = null;
		CardDetails cardDetails = paymentDetails.getCardDetails();
		
		try {
			balance = paymentDAO.getBalanceForCard(cardDetails);
		} 
		catch (DAOException e) {
			setErrorCodeAndThrowException("Error occurred while obtaining balance for card["+cardDetails.getCardNumber()+"]", ErrorCode.BALANCE_UNAVAILABLE, e, paymentDetails);
		} 
		return balance;
	}
	
	/**\
	 * Deducts the total amount from the balance
	 * 
	 * @param balance
	 * @param totalAmount
	 * @param paymentDetails
	 * @throws PaymentServiceException
	 */
	private void deductAmountFromBalance(Double balance, Double totalAmount, PaymentDetails paymentDetails) throws PaymentServiceException{
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
	 * @param balance
	 * @throws PaymentServiceException
	 */
	private void updateBalance(PaymentDetails paymentDetails) throws PaymentServiceException{
		CardDetails cardDetails = paymentDetails.getCardDetails();
		String cardNumber = cardDetails.getCardNumber();
		
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
	 * @param paymentDetails
	 * @throws PaymentServiceException
	 */
	private void setErrorCodeAndThrowException(String errMsg, ErrorCode errorCode, PaymentDetails paymentDetails) throws PaymentServiceException{
		LOG.error(errMsg);
		paymentDetails.getPaymentResult().setErrorCode(errorCode);
		throw new PaymentServiceException(errMsg);
	}
	
	/**
	 * Sets error code in payment result and throws exception
	 * 
	 * @param errMsg
	 * @param paymentDetails
	 * @throws PaymentServiceException
	 */
	private void setErrorCodeAndThrowException(String errMsg, ErrorCode errorCode, Throwable e, PaymentDetails paymentDetails) throws PaymentServiceException{
		LOG.error(errMsg);
		paymentDetails.getPaymentResult().setErrorCode(errorCode);
		throw new PaymentServiceException(errorCode.getDescription(), e);
	}
}
