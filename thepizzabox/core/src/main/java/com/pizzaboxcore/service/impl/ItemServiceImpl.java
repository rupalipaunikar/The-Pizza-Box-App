package com.pizzaboxcore.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizzabox.common.model.Item;
import com.pizzaboxcore.dao.ItemDAO;
import com.pizzaboxcore.service.ItemService;

/**
 * ItemServiceImpl contains the implementation of ItemService APIs 
 * 
 * @author rupalip
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	private static final Logger LOG = Logger.getLogger(ItemServiceImpl.class);
	
	@Autowired
	private ItemDAO itemDAO;
	
	public List<Item> createInitialList() {
		LOG.info("Starting creation of items list.");
	
		List<Item> itemsList = itemDAO.getAllItems();
		
		LOG.info("Completed creation of items map.");
		return itemsList;
	}
}
