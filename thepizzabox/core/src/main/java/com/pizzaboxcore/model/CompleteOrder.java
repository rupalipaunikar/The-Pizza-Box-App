package com.pizzaboxcore.model;

import java.sql.Timestamp;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.User;


/**
 * 
 * @author Roshni
 */

public class CompleteOrder {

	private Integer order_id;
	
	private PaymentType paymentType;
	
	private Status status;

	private Double totalAmount;
	
	private User user;
	
	private Timestamp createdTimestamp;
	
	private Timestamp updatedTimestamp;
	
	private Integer suborder_id;

	private ItemType subOrderType;

	private Integer quantity;

	private Double amount;

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Timestamp createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Timestamp getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public Integer getSuborder_id() {
		return suborder_id;
	}

	public void setSuborder_id(Integer suborder_id) {
		this.suborder_id = suborder_id;
	}

	public ItemType getSubOrderType() {
		return subOrderType;
	}

	public void setSubOrderType(ItemType subOrderType) {
		this.subOrderType = subOrderType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	

	
	
}
