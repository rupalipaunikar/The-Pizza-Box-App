package com.pizzabox.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents items such as pizza, side dishes and beverages
 * 
 * @author rupalip
 *
 */
@Entity
@Table(name = "item")
public class Item {

	@Id
	@Column(name = "item_id")
	private Integer itemId;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "price")
	private Double price;

	public Integer getId() {
		return itemId;
	}

	public void setId(Integer id) {
		this.itemId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", name=" + name + ", type=" + type + ", price=" + price + "]";
	}
}
