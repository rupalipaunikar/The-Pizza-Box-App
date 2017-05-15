package com.payment.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.common.JUnitConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestValidationUtils {

	@Autowired
	private ValidationUtils validationUtils;
	
	@Test
	public void testGetPaymentValidator(){
		Assert.assertNotNull(validationUtils.getPaymentValidator());
	}
	
	@Test
	public void testGetValidator(){
		Assert.assertNotNull(ValidationUtils.getValidator());
	}
}
