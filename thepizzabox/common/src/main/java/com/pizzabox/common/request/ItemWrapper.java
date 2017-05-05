package com.pizzabox.common.request;

import java.util.ArrayList;
import java.util.List;

import com.pizzabox.common.model.Item;


public class ItemWrapper {
	
	private List<Item> itemList;
	
	public ItemWrapper() {
		this.itemList = new ArrayList<Item>();
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
	public void add(Item item){
		itemList.add(item);
	}

}
