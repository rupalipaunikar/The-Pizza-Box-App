package com.pizzaboxcore.dao.impl;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.constants.Constants;
import com.pizzaboxcore.custom.exception.DAOException;
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
	private static final String _WHERE_ID_ = " where id=:id";
	private static final String _UPDATE_ORDER_STATUS_UPDATEDTIMESTAMP = "UPDATE Order SET status =:status, updated_timestamp=:updated_timestamp";

	@Autowired
	private SessionFactory sessionFactory;

	public User getUserDetails(final String userName) throws DAOException {
		LOG.info("Fetching of User Details is initiated.");

		final Query query = sessionFactory.openSession().createQuery(_GET_USER_DETAIL);
		query.setString("username", userName);
		final User user = (User) query.uniqueResult();
		
		if(user==null||user.getUserId()==null){
			LOG.error(Constants.USER_OR_ID_NULL);
			throw new DAOException(Constants.USER_OR_ID_NULL);
		}

		LOG.info("Fetching of User from database is completed.");
		return user;
	}
	
	public Order generateOrder(final Order order) throws DAOException{
		LOG.info("Creating Order in the database.");
		
		final Session session = sessionFactory.openSession();
		final Integer orderId = (Integer) session.save(order);
		
		Order finalOrder = getGeneratedOrder(orderId);
		
		LOG.info("Order Created Successfully");
		return finalOrder;
	}
	
	public Order getGeneratedOrder(Integer orderId) throws DAOException{
		final Session session = sessionFactory.openSession();
		Query query = session.createQuery(_GET_ORDER);
		query.setInteger("id", orderId);

		Order finalOrder = (Order) query.uniqueResult();
		
		if(finalOrder==null||finalOrder.getId()==null){
			LOG.error(Constants.ORDER_OR_ORDERID_NULL);
			throw new DAOException(Constants.ORDER_OR_ORDERID_NULL);
		}
		
		return finalOrder;
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
		final Query query = session.createQuery(_UPDATE_ORDER_STATUS_UPDATEDTIMESTAMP + _WHERE_ID_).setParameter(Constants.STATUS, status)
				.setParameter(Constants.ID, orderId)
				.setParameter(Constants.UPDATEDTIMESTAMP, new Timestamp(System.currentTimeMillis()));
			
		
		final Integer result = query.executeUpdate();
		
		if(result == null || result != 1){
			final String errMsg = "Could not update order status for orderID["+orderId+"]";
			LOG.error(errMsg);
			throw new DAOException(errMsg);
		}
		
		LOG.info("Successfully updated order status["+status.toString()+"] for orderID["+orderId+"]");
	}
}

