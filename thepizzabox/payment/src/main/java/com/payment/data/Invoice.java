package com.payment.data;

import java.sql.Timestamp;

import com.pizzabox.common.constants.PaymentType;

/**
 * Invoice generated depicting the result of the payment
 * 
 * @author rupalip
 *
 */
public class Invoice {

	private String id;
	private Integer orderId;
	private String username;
	private Double amount;
	private Timestamp invoiceTimestamp;
	private PaymentType paymentType;
	private String transactionStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Timestamp getInvoiceTimestamp() {
		return invoiceTimestamp;
	}

	public void setInvoiceTimestamp(Timestamp invoiceTimestamp) {
		this.invoiceTimestamp = invoiceTimestamp;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", orderId=" + orderId + ", username=" + username + ", amount=" + amount
				+ ", invoiceTimestamp=" + invoiceTimestamp + ", paymentType=" + paymentType + ", transactionStatus=" + transactionStatus
				+ "]";
	}

}
