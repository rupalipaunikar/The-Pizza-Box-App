package com.pizzaboxcore.validator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is a utility class for various kinds of validation
 * 
 * @author Roshni
 *
 */
@Component
public class ValidationUtils {

	private static Validator validator;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private ItemValidator itemValidator;

	@Autowired
	private OrderValidator orderValidator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public static Validator getValidator() {
		return validator;
	}

	public UserValidator getPaymentValidator() {
		return userValidator;
	}

	public OrderValidator getOrderValidator() {
		return orderValidator;
	}

	public ItemValidator getItemValidator() {
		return itemValidator;
	}

}
