package com.payment.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
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

	private static final Logger LOG = Logger.getLogger(PaymentDAOImpl.class);
	
	private static final String _WHERE_CARDNUMBER_EXPIRYDATE_CVV = " where cardNumber=:cardNumber and expiryDate=:expiryDate and cvv=:cvv";
	private static final String _WHERE_ID = " where id=:id";
	private static final String _SELECT_BALANCE = "SELECT balance from CardDetails" + _WHERE_CARDNUMBER_EXPIRYDATE_CVV;
	private static final String _UPDATE_BALANCE = "UPDATE CardDetails SET balance =:balance"+ _WHERE_CARDNUMBER_EXPIRYDATE_CVV;
	private static final String _UPDATE_ORDER_STATUS = "UPDATE Order SET status =:status" + _WHERE_ID;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Double getBalanceForCard(final CardDetails cardDetails) throws DAOException {
		String cardNumber = cardDetails.getCardNumber();
		
		Query query = sessionFactory.openSession().createQuery(_SELECT_BALANCE)
				.setParameter(Constants.CARD_NUMBER, cardNumber)
				.setParameter(Constants.EXPIRY_DATE, cardDetails.getExpiryDate())
				.setParameter(Constants.CVV, cardDetails.getCvv());
		
		List<?> list = query.list();
		
		if(list == null || list.isEmpty()){
			String errMsg = "Could not get balance for card["+cardNumber+"]";
			LOG.error(errMsg);
			throw new DAOException(errMsg);
		}
		
		Double balance = (Double) list.get(0);
		LOG.info("Card["+cardNumber+"] has balance["+balance+"]");
		return balance ;
	}

	@Override
	public Integer updateBalance(final CardDetails cardDetails, final Double balance) throws DAOException {
		String cardNumber = cardDetails.getCardNumber();
		
		Query query = sessionFactory.openSession().createQuery(_UPDATE_BALANCE).setParameter(Constants.BALANCE, balance)
				.setParameter(Constants.CARD_NUMBER, cardNumber)
				.setParameter(Constants.EXPIRY_DATE, cardDetails.getExpiryDate())
				.setParameter(Constants.CVV, cardDetails.getCvv());
		
		Integer result = query.executeUpdate();
		
		if(result == null || result != 1){
			String errMsg = "Could not update balance for card["+cardNumber+"]";
			LOG.error(errMsg);
			throw new DAOException(errMsg);
		}
		
		LOG.info("Successfully updated balance["+balance+"] for card["+cardNumber+"]");
		return result; 
	}

	@Override
	public Integer updateOrderStatus(final Integer orderId, final Status status) throws DAOException {
		
		Query query = sessionFactory.openSession().createQuery(_UPDATE_ORDER_STATUS).setParameter(Constants.STATUS, status)
				.setParameter(Constants.ID, orderId);
		
		Integer result = query.executeUpdate();
		
		if(result == null || result != 1){
			String errMsg = "Could not update order status for orderID["+orderId+"]";
			LOG.error(errMsg);
			throw new DAOException(errMsg);
		}
		
		LOG.info("Successfully updated order status["+status.toString()+"] for orderID["+orderId+"]");
		return result;
	}

}
