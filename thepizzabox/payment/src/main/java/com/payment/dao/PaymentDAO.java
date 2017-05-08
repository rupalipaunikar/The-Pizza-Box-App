package com.payment.dao;

import com.pizzabox.common.model.CardDetails;

public interface PaymentDAO {

	Double getBalanceForCard(CardDetails cardDetails);
	Integer updateBalance(CardDetails cardDetails, Double balance);
}
