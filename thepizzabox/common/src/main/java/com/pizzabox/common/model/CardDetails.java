package com.pizzabox.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="card_details")
public class CardDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	@OneToOne
	@Id
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="card_number",unique=true)
	private String cardNumber;
	
	@Column(name="expiry_date")
	private String expiryDate;
	
	@Column(name="cvv")
	private int cvv;
	
	@Column(name="balance")
	private Double balance;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
