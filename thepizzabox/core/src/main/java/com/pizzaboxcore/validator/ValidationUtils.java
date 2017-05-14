package com.pizzaboxcore.validator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is a utility class for various kinds of validation
 * 
 * @author rupalip
 *
 */
@Component
public class ValidationUtils {

	private static Validator validator;
	
	@Autowired
	private ItemValidator itemValidator;
	
	static{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

	public static Validator getValidator() {
		return validator;
	}

	public ItemValidator getItemValidator() {
		return itemValidator;
	}
}
