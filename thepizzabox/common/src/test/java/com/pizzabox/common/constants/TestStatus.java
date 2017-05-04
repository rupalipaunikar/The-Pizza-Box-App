package com.pizzabox.common.constants;

import org.junit.Assert;
import org.junit.Test;

import com.pizzabox.common.constants.Status;


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
