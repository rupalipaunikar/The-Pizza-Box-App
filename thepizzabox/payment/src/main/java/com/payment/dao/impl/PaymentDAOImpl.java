package com.payment.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.dao.PaymentDAO;
import com.payment.exception.DAOException;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.CardDetails;

/**
 * PaymentDAOImpl contains the implementation of PaymentDAO APIs 
 * 
 * @author rupalip
 *
 */
@Repository
public class PaymentDAOImpl implements PaymentDAO {

	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(PaymentDAOImpl.class);
	
	private static final String _WHERE_CARDNUMBER_EXPIRYDATE_CVV_USERID = " where cardNumber=:cardNumber and expiryDate=:expiryDate "
																	+ "and cvv=:cvv and user.userId=:userId";
	private static final String _WHERE_ID = " where id=:id";
	private static final String _SELECT_BALANCE = "SELECT balance from CardDetails";
	private static final String _UPDATE_BALANCE = "UPDATE CardDetails SET balance =:balance";
	private static final String _UPDATE_ORDER_STATUS = "UPDATE Order SET status =:status";

	/**
	 * SessionFactory to interact with the database
	 */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * This API retrieves the balance for a card
	 * 
	 * @param cardDetails
	 * 			CardDetails entered by the user
	 * @return balance
	 * 			Balance to be retrieved for the given card
	 * @throws DAOException 
	 */
	@Override
	public Double getBalanceForCard(final CardDetails cardDetails) throws DAOException {
		final String cardNumber = cardDetails.getCardNumber();
		
		final Session session = sessionFactory.openSession();
		final Query query = session.createQuery(_SELECT_BALANCE + _WHERE_CARDNUMBER_EXPIRYDATE_CVV_USERID)
				.setParameter(Constants.CARD_NUMBER, cardNumber)
				.setParameter(Constants.EXPIRY_DATE, cardDetails.getExpiryDate())
				.setParameter(Constants.CVV, cardDetails.getCvv())
				.setParameter(Constants.USER_ID, cardDetails.getUser().getUserId());
		
		final List<?> list = query.list();
		
		if(list == null || list.isEmpty()){
			final String errMsg = "Could not get balance for card["+cardNumber+"]";
			LOG.error(errMsg);
			throw new DAOException(errMsg);
		}
		
		final Double balance = (Double) list.get(0);
		LOG.info("Card["+cardNumber+"] has balance["+balance+"]");
		return balance ;
	}

	/**
	 * This API updates the balance for a card
	 * 
	 * @param cardDetails
	 * 			CardDetails entered by the user
	 * @throws DAOException 
	 */
	@Override
	public void updateBalance(final CardDetails cardDetails) throws DAOException {
		final String cardNumber = cardDetails.getCardNumber();
		
		final Session session = sessionFactory.openSession();
		final Query query = session.createQuery(_UPDATE_BALANCE+_WHERE_CARDNUMBER_EXPIRYDATE_CVV_USERID)
				.setParameter(Constants.BALANCE, cardDetails.getBalance())
				.setParameter(Constants.CARD_NUMBER, cardNumber)
				.setParameter(Constants.EXPIRY_DATE, cardDetails.getExpiryDate())
				.setParameter(Constants.CVV, cardDetails.getCvv())
				.setParameter(Constants.USER_ID, cardDetails.getUser().getUserId());
		
		final Integer result = query.executeUpdate();
		
		if(result == null || result != 1){
			final String errMsg = "Could not update balance for card["+cardNumber+"]";
			LOG.error(errMsg);
			throw new DAOException(errMsg);
		}
		
		LOG.info("Successfully updated balance["+cardDetails.getBalance()+"] for card["+cardNumber+"]");
	}

	/**
	 * This API updates the order status with the given status
	 * 
	 * @param orderId
	 * 			ID of the order being processed
	 * @param status
	 * 			Order status to be updated
	 * @throws DAOException 
	 */
	@Override
	public void updateOrderStatus(final Integer orderId, final Status status) throws DAOException {
		
		final Session session = sessionFactory.openSession();
		final Query query = session.createQuery(_UPDATE_ORDER_STATUS + _WHERE_ID).setParameter(Constants.STATUS, status)
				.setParameter(Constants.ID, orderId);
			
		
		final Integer result = query.executeUpdate();
		
		if(result == null || result != 1){
			final String errMsg = "Could not update order status for orderID["+orderId+"]";
			LOG.error(errMsg);
			throw new DAOException(errMsg);
		}
		
		LOG.info("Successfully updated order status["+status.toString()+"] for orderID["+orderId+"]");
	}

}
