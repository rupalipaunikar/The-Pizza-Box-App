package com.pizzabox.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.ValidationErrorMessage;

@Entity
@Table(name = "card_details")
public class CardDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@OneToOne
	@Id
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "card_number", unique = true)
	private String cardNumber;

	@Column(name = "expiry_date")
	private String expiryDate;

	@Column(name = "cvv")
	private Integer cvv;

	@Column(name = "balance")
	private Double balance;
	
	public CardDetails() {}
	
	public CardDetails(User user, String cardNumber, String expiryDate, Integer cvv) {
		super();
		this.user = user;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.cvv = cvv;
	}

	@NotBlank(message=ValidationErrorMessage.CARD_NUMBER_BLANK)
	@Size(min=16, max=16, message=ValidationErrorMessage.CARD_NUMBER_16_DIGIT)
	@Pattern(regexp=Constants.REGEX_ONLY_DIGITS, message=ValidationErrorMessage.CARD_NUMBER_CANNOT_HAVE_ALPHABETS)
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	@NotBlank(message=ValidationErrorMessage.EXPIRY_DATE_BLANK)
	@Size(min=5, max=5, message=ValidationErrorMessage.EXPIRY_DATE_INVALID)
	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	@NotNull(message=ValidationErrorMessage.CVV_BLANK)
	@NumberFormat(style=Style.NUMBER)
	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "CardDetails [user=" + user + ", cardNumber=" + cardNumber + ", expiryDate=" + expiryDate + ", cvv="
				+ cvv + ", balance=" + balance + "]";
	}
}
