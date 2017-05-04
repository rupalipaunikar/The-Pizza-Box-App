package com.pizzaboxcore.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pizzabox.common.model.Item;
import com.pizzaboxcore.dao.ItemDAO;

/**
 * ItemDAOImpl contains the implementation of ItemDAO APIs 
 * 
 * @author rupalip
 *
 */
@Repository
public class ItemDAOImpl implements ItemDAO{

	private static final Logger LOG = Logger.getLogger(ItemDAOImpl.class);
	
	public static final String _SELECT_ALL = "from Item";
	
	@Autowired
    private SessionFactory sessionFactory;

	public List<Item> getAllItems() {
		LOG.info("Fetching of items from database is initiated.");
		
		Query query = sessionFactory.openSession().createQuery(_SELECT_ALL);
		
		LOG.info("Fetching of items from database is completed.");
		return (List<Item>) query.list();
	}
}
