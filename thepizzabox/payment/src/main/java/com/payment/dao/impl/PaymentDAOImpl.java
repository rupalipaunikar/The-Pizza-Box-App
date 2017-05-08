package com.payment.dao.impl;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.dao.PaymentDAO;
import com.pizzabox.common.model.CardDetails;

@Repository
public class PaymentDAOImpl implements PaymentDAO{

	private static final String _WHERE_CARDNUMBER_EXPIRYDATE_CVV = " where cardNumber=:cardNumber and expiryDate=:expiryDate and cvv=:cvv";
	private static final String _SELECT_BALANCE = "SELECT balance from CardDetails" + _WHERE_CARDNUMBER_EXPIRYDATE_CVV;
	private static final String _UPDATE_BALANCE = "UPDATE CardDetails SET balance =:balance"+ _WHERE_CARDNUMBER_EXPIRYDATE_CVV;
	
	
	@Autowired
    private SessionFactory sessionFactory;

	
	@Override
	public Double getBalanceForCard(CardDetails cardDetails) {
		Query query = sessionFactory.openSession().createQuery(_SELECT_BALANCE)
												  .setParameter("cardNumber", cardDetails.getCardNumber())
												  .setParameter("expiryDate", cardDetails.getExpiryDate())
												  .setParameter("cvv", cardDetails.getCvv());
		return (Double) query.list().get(0);
	}


	@Override
	public Integer updateBalance(CardDetails cardDetails, Double balance) {
		Query query = sessionFactory.openSession().createQuery(_UPDATE_BALANCE)
												  .setParameter("balance", balance)
												  .setParameter("cardNumber", cardDetails.getCardNumber())
												  .setParameter("expiryDate", cardDetails.getExpiryDate())
												  .setParameter("cvv", cardDetails.getCvv());
		return query.executeUpdate();
	}

}
