package com.pizzaboxcore.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.JUnitConstants;
import com.pizzabox.common.model.Item;
import com.pizzaboxcore.dao.ItemDAO;

/**
 * 
 * @author rupalip
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations=JUnitConstants.LOCATION_APP_CONTEXT)  
public class TestItemDAOImpl {

	@Autowired
	private ItemDAO itemDAO;
	
	@Test
	public void testGetAllItems(){
		List<Item> actualItems = itemDAO.getAllItems();
		Assert.assertNotNull(actualItems);
		
		List<Item> expectedItems = getDummyList(JUnitConstants.LOCATION_DUMMY_ITEMS_LIST);
		Assert.assertTrue(expectedItems.equals(actualItems));
	}
	
	private List<Item> getDummyList(String filename){
		List<Item> items = new ArrayList<Item>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
			String line = null;
			while((line = br.readLine()) != null){
				String[] parts = line.split(JUnitConstants.COMMA_SEPARATOR);
				Item item = new Item(Integer.parseInt(parts[0]), parts[1].trim(), Double.parseDouble(parts[2]), ItemType.getItemType(parts[3]));
				items.add(item);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return items;
	}
}
