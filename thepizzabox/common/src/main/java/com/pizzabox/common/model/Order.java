package com.pizzabox.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;



/**
 * Represents an order which can have multiple suborders
 * 
 * @author rupalip
 *
 */
@Entity
@Table(name="order_details")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="order_id")
	private Integer id;
	
	@Column(name="payment_type")
	private PaymentType paymentType;
	
	@Column(name="status")
	private Status status;

	@OneToMany(mappedBy = "order", cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private List<SubOrder> subOrders = new ArrayList<SubOrder>(0);
	
	@Column(name="total_amount")
	private Double totalAmount;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="created_timestamp")
	private Timestamp createdTimestamp;
	
	@Column(name="updated_timestamp")
	private Timestamp updatedTimestamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//@NotBlank(message="Please select ")
	//@NotNull(message="Please select ")
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

	public List<SubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<SubOrder> subOrders) {
		this.subOrders = subOrders;
	}

	public void addToSubOrders(SubOrder subOrder) {
		subOrder.setOrder(this);
        this.subOrders.add(subOrder);
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

	public Order(Integer id, PaymentType paymentType, Status status, List<SubOrder> subOrders, Double totalAmount,
			User user, Timestamp createdTimestamp, Timestamp updatedTimestamp) {
		super();
		this.id = id;
		this.paymentType = paymentType;
		this.status = status;
		this.subOrders = subOrders;
		this.totalAmount = totalAmount;
		this.user = user;
		this.createdTimestamp = createdTimestamp;
		this.updatedTimestamp = updatedTimestamp;
	}
	
	public Order(PaymentType paymentType, Status status, List<SubOrder> subOrders, Double totalAmount,
			User user, Timestamp createdTimestamp, Timestamp updatedTimestamp) {
		super();
		this.paymentType = paymentType;
		this.status = status;
		this.subOrders = subOrders;
		this.totalAmount = totalAmount;
		this.user = user;
		this.createdTimestamp = createdTimestamp;
		this.updatedTimestamp = updatedTimestamp;
	}

	public Order() {
		super();
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", paymentType=" + paymentType + ", status=" + status 
				+ ", totalAmount=" + totalAmount + ", user=" + user + ", createdTimestamp=" + createdTimestamp
				+ ", updatedTimestamp=" + updatedTimestamp + "]";
	}
}
