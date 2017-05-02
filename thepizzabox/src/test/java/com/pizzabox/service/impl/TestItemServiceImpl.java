package com.pizzabox.service.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzabox.constants.JUnitConstants;
import com.pizzabox.model.Item;
import com.pizzabox.service.ItemService;

import junit.framework.Assert;

/**
 * 
 * @author rupalip
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations=JUnitConstants.LOCATION_APP_CONTEXT)  
public class TestItemServiceImpl {

	@Autowired
	private ItemService itemService;
	
	@Test
	public void testCreateInitialItemDataMap(){
		Map<String, List<Item>> map = itemService.createInitialItemDataMap();
		Assert.assertNotNull(map);
		Assert.assertEquals(3, map.size());
	}
	
}