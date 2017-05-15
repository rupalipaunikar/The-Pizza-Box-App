package com.payment.common;

import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.User;

public class JUnitTestHelper {

	public static User createUser(){
		User user = new User();
		user.setUserId(1);
		user.setUsername(Constants.USER);
		return user;
	}
	
	public static CardDetails createCardDetails(String cardNumber, User user){
		CardDetails cardDetails = new CardDetails(user, cardNumber, JUnitConstants.EXPIRY_DATE, JUnitConstants.CVV);
		cardDetails.setBalance(2000.0);
		return cardDetails;
	}
}
