package com.payment.data;

import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;

/**
 * This is a wrapper class for the messaging system to work with
 * 
 * @author rupalip
 *
 */
public class PaymentDetails {

	private CardDetails cardDetails;
	private Order order;
	private PaymentResult paymentResult;

	public PaymentDetails() {
	}

	public PaymentDetails(CardDetails cardDetails, Order order, PaymentResult paymentResult) {
		super();
		this.cardDetails = cardDetails;
		this.order = order;
		this.paymentResult = paymentResult;
	}

	public CardDetails getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public PaymentResult getPaymentResult() {
		return paymentResult;
	}

	public void setPaymentResult(PaymentResult paymentResult) {
		this.paymentResult = paymentResult;
	}

}
