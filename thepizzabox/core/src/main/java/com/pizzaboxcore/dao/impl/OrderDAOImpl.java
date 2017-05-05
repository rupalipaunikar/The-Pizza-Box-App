package com.pizzaboxcore.dao.impl;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.dao.OrderDAO;

/**
 * OrderDAOImpl contains the implementation of OrderDAO APIs
 * 
 * @author Roshni
 *
 */

@Repository
public class OrderDAOImpl implements OrderDAO {
	private static final Logger LOG = Logger.getLogger(OrderDAOImpl.class);

	public static final String _GET_USER_DETAIL = "from User user where user.username =:username";
	public static final String _GET_ORDER = "from Order where id=:id ";

	@Autowired
	private SessionFactory sessionFactory;

	public User getUserDetails(final String userName) {
		LOG.info("Fetching of User Details is initiated.");

		Query query = sessionFactory.openSession().createQuery(_GET_USER_DETAIL);
		query.setString("username", userName);
		final User user = (User) query.uniqueResult();

		LOG.info("Fetching of User from database is completed.");
		return user;
	}
	
	public Order generateOrder(Order order){
		LOG.info("Creating Order in the database.");
		
		
		final Session session = sessionFactory.openSession();
		session.beginTransaction();
		int orderId = (int) session.save(order);
		session.getTransaction().commit();
		session.close();
		
		Query query = sessionFactory.openSession().createQuery(_GET_ORDER);
		query.setInteger("id", orderId);
		Order finalOrder = (Order) query.uniqueResult();
		LOG.info("Order Created Successfully");
		return finalOrder;
	}
}

