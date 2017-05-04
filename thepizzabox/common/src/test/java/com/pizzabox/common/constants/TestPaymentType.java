package com.pizzabox.common.constants;

import org.junit.Assert;
import org.junit.Test;

import com.pizzabox.common.constants.PaymentType;


/**
 * 
 * @author rupalip
 *
 */
public class TestPaymentType {

	@Test
	public void testGetPaymentType(){
		PaymentType actualPaymentType = PaymentType.getPaymentType(0);
		
		Assert.assertEquals(PaymentType.CASH, actualPaymentType);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidValue(){
		PaymentType.getPaymentType(8);
	}
}
