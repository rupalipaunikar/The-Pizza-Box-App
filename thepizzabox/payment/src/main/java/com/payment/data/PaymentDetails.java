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
	private PaymentResultStatus paymentResultStatus;

	public PaymentDetails() {}

	public PaymentDetails(CardDetails cardDetails, Order order) {
		super();
		this.cardDetails = cardDetails;
		this.order = order;
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

	public PaymentResultStatus getPaymentResultStatus() {
		return paymentResultStatus;
	}

	public void setPaymentResultStatus(PaymentResultStatus paymentResultStatus) {
		this.paymentResultStatus = paymentResultStatus;
	}

	@Override
	public String toString() {
		return "PaymentDetails [cardDetails=" + cardDetails + ", order=" + order + ", paymentResultStatus="
				+ paymentResultStatus + "]";
	}

}
