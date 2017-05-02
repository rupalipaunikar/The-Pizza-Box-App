package com.pizzabox.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizzabox.dao.ItemDAO;
import com.pizzabox.model.Item;
import com.pizzabox.service.ItemService;

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
	
	public Map<String, List<Item>> createInitialItemDataMap() {
		LOG.info("Starting creation of items map.");
	
		List<Item> items = itemDAO.getAllItems();
		
		Map<String, List<Item>> itemsMap = new HashMap<String, List<Item>>();
		for(Item item : items){
			populateItemsMap(itemsMap, item);
		}
		
		LOG.info("Completed creation of items map.");
		return itemsMap;
	}
	
	/**
	 * This is a generic method to populate the map by categorizing it on 
	 * the basis of item type
	 * 
	 * @param itemsMap
	 * @param item
	 */
	private void populateItemsMap(Map<String, List<Item>> itemsMap, Item item){
		LOG.debug("Populating item[{"+item.getName()+"}] in the map.");
		
		if(itemsMap.containsKey(item.getType().toString())){
			itemsMap.get(item.getType().toString()).add(item);
		}
		else{
			List<Item> items = new ArrayList<Item>();
			items.add(item);
			itemsMap.put(item.getType().toString(), items);
		}
		LOG.debug("Population of item[{"+item.getName()+"}] completed.");
	}
}
