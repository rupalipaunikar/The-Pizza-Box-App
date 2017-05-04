package com.pizzabox.common.constants;

import org.apache.log4j.Logger;


/**
 * This enum holds values for the item type
 * 
 * @author rupalip
 *
 */
public enum ItemType {

	PIZZA("pizza"), SIDES("sides"), BEVERAGE("beverage");
	
	private static final Logger LOG = Logger.getLogger(ItemType.class);
	
	private String type;
	
	private ItemType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * This is a utility method to return the ItemType enum based on the 
	 * incoming int value
	 * 
	 * @param type
	 * @return ItemType
	 */
	public static ItemType getItemType(String type){
		ItemType[] types = ItemType.values();
		
		for(ItemType iType : types){
			if(iType.getType().equalsIgnoreCase(type.trim())){
				return iType;
			}
		}
		String message = "ItemType["+type+"] is invalid.";
		LOG.error(message);
		throw new IllegalArgumentException(message);
	}
}


