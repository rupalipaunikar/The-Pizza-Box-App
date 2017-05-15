package com.pizzaboxcore.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.internal.jaxb.mapping.orm.JaxbUniqueConstraint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.constants.JUnitConstants;
import com.pizzaboxcore.custom.exception.DAOException;
import com.pizzaboxcore.dao.OrderDAO;

/**
 * 
 * @author Roshni
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations=JUnitConstants.LOCATION_APP_CONTEXT)
public class TestOrderDAOImpl  {
	
	@Autowired
	private OrderDAO orderDao;

	 @Test
	 @Transactional
	 @Rollback(true)
	 public void testGetUserDetails() throws DAOException{
		 User user = orderDao.getUserDetails(JUnitConstants.USERNAME);
		 Assert.assertNotNull(user);
			Assert.assertEquals(JUnitConstants.USERNAME, user.getUsername());
			Assert.assertEquals(JUnitConstants.USER.getFirstName(), user.getFirstName());
			Assert.assertEquals(JUnitConstants.USER.getLastName(), user.getLastName());
			Assert.assertEquals(JUnitConstants.USER.getRole(), user.getRole());
			Assert.assertEquals(JUnitConstants.USER.getContactNo(), user.getContactNo());
			Assert.assertEquals(JUnitConstants.USER.getAddress(), user.getAddress());
	 }
	 
	 @Test
	 @Transactional
	 @Rollback(true)
	 public void testGenerateOrder() throws DAOException{
		 
		 Order order = orderDao.generateOrder(JUnitConstants.GENERATE_ORDER);
		 assertNotNull(order);
			assertEquals(PaymentType.NOTSELECTED, order.getPaymentType());
			assertEquals(Status.SUBMITTED, order.getStatus());
			assertEquals(JUnitConstants.USERNAME, order.getUser().getUsername());
	 }
	 
	 @Test(expected=DAOException.class)
	 public void testUserNotFoundException() throws DAOException{
		 orderDao.getUserDetails(JUnitConstants.INVALID_USERNAME);
	 }
	 
	 @Test(expected=DAOException.class)
	 public void testOrderGenerationException() throws DAOException{
		orderDao.getGeneratedOrder(JUnitConstants.INVALID_ORDER_ID);
	 }
}
