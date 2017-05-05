package com.pizzaboxcore.service.impl;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzabox.common.constants.JUnitConstants;
import com.pizzabox.common.model.Item;
import com.pizzaboxcore.service.ItemService;


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
		List<Item> map = itemService.createInitialList();
		Assert.assertNotNull(map);
		Assert.assertEquals(3, map.size());
	}
	
}
