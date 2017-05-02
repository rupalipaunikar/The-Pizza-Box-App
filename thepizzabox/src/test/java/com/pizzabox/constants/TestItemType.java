package com.pizzabox.constants;

import org.junit.Test;

import junit.framework.Assert;

/**
 * 
 * @author rupalip
 *
 */
public class TestItemType {

	private static final String PIZZA = "pizza";
	private static final String INVALID = "invalid";
	
	@Test
	public void testGetItemType(){
		ItemType actualItemType = ItemType.getItemType(PIZZA);
		
		Assert.assertEquals(ItemType.PIZZA, actualItemType);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidValue(){
		ItemType.getItemType(INVALID);
	}
}
