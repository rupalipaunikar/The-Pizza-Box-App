package com.pizzabox.dao;

import java.util.List;

import com.pizzabox.model.Item;

/**
 * ItemDAO contains the APIs to interact with Item table
 * 
 * @author rupalip
 *
 */
public interface ItemDAO {

	/**
	 * This API returns a list of all items from the database
	 * 
	 * @return list of items
	 */
	List<Item> getAllItems();
}
