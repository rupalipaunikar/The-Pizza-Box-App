package com.pizzaboxcore.validator;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pizzabox.common.model.User;
import com.pizzaboxcore.constants.Constants;
import com.pizzaboxcore.custom.exception.CustomGenericException;
import com.pizzaboxcore.service.impl.OrderServiceImpl;

@Component
public class UserValidator {
	
	private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class);
	
	public void validateUser(User user){
		if(user==null){
			LOG.info(Constants.USER_DETAILS_NULL);
			throw new CustomGenericException(Constants.USER_DETAILS_NULL);
		}
	}
	
	public void validatePrincipalObject(Principal user){
		if(user==null){
			LOG.info(Constants.PRINCIPAL_OBJECT_NULL);
			throw new CustomGenericException(Constants.PRINCIPAL_OBJECT_NULL);
		}
	}
}
