package com.pizzabox.constants;

import org.junit.Test;

import junit.framework.Assert;

/**
 * 
 * @author rupalip
 *
 */
public class TestStatus {

	@Test
	public void testGetStatus(){
		Status actualStatus = Status.getStatus(0);
		Assert.assertEquals(Status.SUBMITTED, actualStatus);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidValue(){
		Status.getStatus(8);
	}
}
