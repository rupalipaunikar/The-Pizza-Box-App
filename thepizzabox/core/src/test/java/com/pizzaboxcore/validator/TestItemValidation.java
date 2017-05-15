package com.pizzaboxcore.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzaboxcore.constants.JUnitConstants;
import com.pizzaboxcore.custom.exception.NoItemFound;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = JUnitConstants.LOCATION_APP_CONTEXT)
public class TestItemValidation {

	@Autowired
	private ItemValidator itemValidator;
	
	@Test(expected=NoItemFound.class)
	public void testValidateItemList() throws NoItemFound{
		itemValidator.validateItemList(null);
	}
	
	@Test(expected=NoItemFound.class)
	public void testvalidateItemWrapper() throws NoItemFound{
		itemValidator.validateItemWrapper(null);
	}
}
