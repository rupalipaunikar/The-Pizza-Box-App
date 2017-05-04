package com.pizzaboxcore.service.impl;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzaboxcore.constants.JUnitConstants;
import com.pizzaboxcore.model.Item;
import com.pizzaboxcore.service.ItemService;


/**
 * 
 * @author rupalip
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={JUnitConstants.LOCATION_APP_CONTEXT, JUnitConstants.LOCATION_PAYMENT_INT_CONTEXT})   
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
