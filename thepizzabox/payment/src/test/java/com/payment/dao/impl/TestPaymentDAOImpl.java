package com.payment.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.common.JUnitConstants;
import com.payment.common.JUnitTestHelper;
import com.payment.dao.PaymentDAO;
import com.payment.exception.DAOException;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestPaymentDAOImpl {

	@Autowired
	private PaymentDAO paymentDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void testGetBalanceForCard() throws DAOException {
		CardDetails cardDetails = JUnitTestHelper.createCardDetails(JUnitConstants.CARD_NUMBER, JUnitTestHelper.createUser());
		Double balance = paymentDAO.getBalanceForCard(cardDetails);
		
		Assert.assertNotNull(balance);
		Assert.assertTrue(2000.0 == balance.doubleValue());
	}
	
	@Test(expected=DAOException.class)
	public void testGetBalanceForCardException() throws DAOException {
		CardDetails cardDetails = JUnitTestHelper.createCardDetails(JUnitConstants.INVALID_CARD_NUMBER, JUnitTestHelper.createUser());
		paymentDAO.getBalanceForCard(cardDetails);
	}

	@Test
	public void testUpdateBalance() throws DAOException {
		CardDetails cardDetails = JUnitTestHelper.createCardDetails(JUnitConstants.CARD_NUMBER, JUnitTestHelper.createUser());
		paymentDAO.updateBalance(cardDetails);
	}

	@Test(expected=DAOException.class)
	public void testUpdateBalanceExcpetion() throws DAOException {
		CardDetails cardDetails = JUnitTestHelper.createCardDetails(JUnitConstants.INVALID_CARD_NUMBER, JUnitTestHelper.createUser());
		paymentDAO.updateBalance(cardDetails);
	}
	
	@Test
	public void updateOrderStatus() throws DAOException {
		Order order = new Order();
		order.setStatus(Status.SUBMITTED);
		order.setPaymentType(PaymentType.NOTSELECTED);
		Session session = sessionFactory.openSession();
		int orderId = (int) session.save(order);
		
		paymentDAO.updateOrderStatus(orderId, Status.PAID_CASH);
	}
	
	@Test(expected=DAOException.class)
	public void updateOrderStatusException() throws DAOException {
		paymentDAO.updateOrderStatus(100, Status.PAID_CASH);
	}
}
