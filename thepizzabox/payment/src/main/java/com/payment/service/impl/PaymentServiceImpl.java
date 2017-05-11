package com.payment.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.payment.dao.PaymentDAO;
import com.payment.data.PaymentDetails;
import com.payment.exception.DAOException;
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
@Transactional(propagation=Propagation.REQUIRED)
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentDAO paymentDAO;

	@Override
	public void executePayment(PaymentDetails paymentDetails) throws PaymentServiceException {
		CardDetails cardDetails = paymentDetails.getCardDetails();
		String cardNumber = cardDetails.getCardNumber();
		Order order = paymentDetails.getOrder();
		Double totalAmount = order.getTotalAmount();
		
		LOG.info("Initiating payment execution for order ID["+order.getId()+"]");
		
		try {
			Double balance = paymentDAO.getBalanceForCard(cardDetails);

			if (balance == null || balance < totalAmount) {
				String errMsg = "Insufficient balance in card["+cardNumber+"]";
				LOG.error(errMsg);
				throw new PaymentServiceException(errMsg);
			}
			
			LOG.info("Updating balance for card["+cardNumber+"]");
			updateBalance(balance, totalAmount, cardDetails);

		} 
		catch (DAOException e) {
			LOG.error("Error occurred while executing payment for card["+cardNumber+"]");
			throw new PaymentServiceException(e);
		} 
		
		LOG.info("Payment execution is complete for order ID["+order.getId()+"]");
	}

	@Override
	public void updateOrderStatus(Integer orderId, Status status) throws PaymentServiceException {
		LOG.info("Updating order status to "+status.getStatus()+" for order ID["+orderId+"]");
		
		try {
			Integer result = paymentDAO.updateOrderStatus(orderId, status);
			
			if (result == null || result != 1) {
				String errMsg = "Could not update balance for order ID["+orderId+"]";
				LOG.error(errMsg);
				throw new PaymentServiceException(errMsg);
			}
		} 
		catch (DAOException e) {
			LOG.error("Error occurred while updating order status for order ID["+orderId+"]");
			throw new PaymentServiceException(e);
		}
	}
	
	/**
	 * This method updates deducts total amount from the balance and updates in the 
	 * database
	 * 
	 * @param balance
	 * @param totalAmount
	 * @param cardDetails
	 * @throws DAOException
	 * @throws PaymentServiceException
	 */
	private void updateBalance(Double balance, Double totalAmount, CardDetails cardDetails) throws DAOException, PaymentServiceException{
		balance -= totalAmount;
		Integer result = paymentDAO.updateBalance(cardDetails, balance);
		
		if (result == null || result != 1) {
			String errMsg = "Could not update balance for card["+cardDetails.getCardNumber()+"]";
			LOG.error(errMsg);
			throw new PaymentServiceException(errMsg);
		}
	}
}
