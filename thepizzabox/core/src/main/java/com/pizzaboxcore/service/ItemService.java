package com.pizzaboxcore.service;

import java.util.List;
import java.util.Map;

import com.pizzabox.common.model.Item;

/**
 * ItemService contains the APIs to interact with Item related services
 * 
 * @author rupalip
 *
 */
public interface ItemService {

	/**
	 * This API creates a map of items which has item type as its key and list
	 * of items as the value. This map will initially have 3 entries pertaining 
	 * to 3 item type defined currently - pizza, sides and beverage
	 * 
	 * @return map
	 */
	List<Item> createInitialList();
}
