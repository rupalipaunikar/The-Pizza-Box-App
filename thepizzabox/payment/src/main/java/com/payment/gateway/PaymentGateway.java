package com.payment.gateway;

import com.pizzabox.common.model.Order;

public interface PaymentGateway {

	void makePayment(Order o);
}
