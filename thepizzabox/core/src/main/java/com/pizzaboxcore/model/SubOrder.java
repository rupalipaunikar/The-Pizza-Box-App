package com.pizzaboxcore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Represents details of a suborder specific to the item being processed.
 * Each suborder is a part of an order
 *  
 * @author rupalip
 *
 */
@Entity
@Table(name = "suborder_details")
public class SubOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "suborder_id")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "amount")
	private Double amount;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "SubOrder [id=" + id + ", item=" + item + ", quantity=" + quantity + ", amount=" + amount + ", order="
				+ order + "]";
	}
}
