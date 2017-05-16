package com.pizzabox.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pizzabox.common.constants.ItemType;

/**
 * Represents details of a suborder specific to the item being processed. Each
 * suborder is a part of an order
 * 
 * @author rupalip
 *
 */
@Entity
@Table(name = "suborder_details")
public class SubOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "suborder_id")
	private Integer id;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type")
	private ItemType subOrderType;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "amount")
	private Double amount;

	@ManyToOne
	@JoinColumn(name = "order_id", insertable = true, updatable = true)
	private Order order;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "suborders_items", joinColumns = @JoinColumn(name = "suborder_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<Item> items = new ArrayList<Item>();

	public SubOrder() {
		super();
	}

	public SubOrder(Integer id, ItemType subOrderType, Integer quantity, Double amount) {
		super();
		this.id = id;
		this.subOrderType = subOrderType;
		this.quantity = quantity;
		this.amount = amount;
	}

	public SubOrder(ItemType subOrderType, Integer quantity, Double amount) {
		super();
		this.subOrderType = subOrderType;
		this.quantity = quantity;
		this.amount = amount;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addToItems(Item item) {
		this.items.add(item);
	}

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "SubOrder [id=" + id + ", Type=" + subOrderType + ", quantity=" + quantity + ", amount=" + amount + "]";
	}

	public ItemType getSubOrderType() {
		return subOrderType;
	}

	public void setSubOrderType(ItemType subOrderType) {
		this.subOrderType = subOrderType;
	}
}
