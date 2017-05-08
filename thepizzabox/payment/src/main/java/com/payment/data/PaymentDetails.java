package com.payment.data;

import com.pizzabox.common.model.CardDetails;

public class PaymentDetails {

	private CardDetails cardDetails;
	private Double totalAmount;

	public PaymentDetails() {}
	
	public PaymentDetails(CardDetails cardDetails, Double totalAmount) {
		super();
		this.cardDetails = cardDetails;
		this.totalAmount = totalAmount;
	}

	public CardDetails getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
