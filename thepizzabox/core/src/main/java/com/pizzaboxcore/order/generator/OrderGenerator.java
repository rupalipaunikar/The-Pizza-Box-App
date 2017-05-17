package com.pizzaboxcore.order.generator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.SubOrder;
import com.pizzabox.common.model.User;

/**
 * 
 * @author Roshni
 *
 */

@Component
public class OrderGenerator {
	private static final Logger LOG = Logger.getLogger(OrderGenerator.class);
	
	/**
	 * This method creates an order based on the item select by the user and
	 * return the Order object created
	 * 
	 * @param finalItemList
	 *            This contains all the items(Pizza,Beverage,Sides)details
	 *            selected by the user
	 * @param checkBoxList
	 *            This contains the items selected by the user
	 * @param user This contains the details of the logged in user
	 * @return Order
	 */
	
	public Order generateOrder(final List<Item> finalItemList,
			final User user) {
		LOG.info("Order Creation Started by Order Generator");
		

		final Double totalAmount = calculateTotalPrice(finalItemList);
		final List<SubOrder> subOrderList = generateSubOrderList(finalItemList);

		final Order order = new Order();

		order.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		order.setUser(user);
		order.setTotalAmount(totalAmount);
		order.setStatus(Status.SUBMITTED);
		order.setSubOrders(subOrderList);
		order.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		order.setPaymentType(PaymentType.NOTSELECTED);
		
		int listSize = subOrderList.size();
		
		for(int counter=0;counter<listSize;counter++){
			order.addToSubOrders(subOrderList.get(counter));
		} 
		
		/*order.addToSubOrders(subOrderList.get(0));
		order.addToSubOrders(subOrderList.get(1));
		order.addToSubOrders(subOrderList.get(2));*/


		LOG.info("Order Created for the user " + user.getUsername());
		return order;
	}
	

	/**
	 * This method calculates the final billing price based on user selection
	 * 
	 * @param finalOrderList
	 *            This contains all the items(Pizza,Beverage,Sides)details
	 *            selected by the user
	 */
	public Double calculateTotalPrice(final List<Item> finalOrderList) {
		Iterator iteratorCheckBox = finalOrderList.iterator();
		Double totalPrice = 0.0;
		int counter = 0;
		while (iteratorCheckBox.hasNext() && (counter != finalOrderList.size())) {
			totalPrice = totalPrice + finalOrderList.get(counter).getPrice() * finalOrderList.get(counter).getQuantity();
			counter++;
			iteratorCheckBox.next();
		}

		return totalPrice;
	}

	/**
	 * This method generates the suborder list by dividing the items selected by
	 * the User based on their type
	 * 
	 * @param itemList
	 *            This contains all the items(Pizza,Beverage,Sides)details
	 *            selected by the user
	 * @return List<SubOrder>
	 */
	public List<SubOrder> generateSubOrderList(final List<Item> itemList) {
		LOG.info("Creating Sub-order List");
		final List<SubOrder> subOrderList = new ArrayList<SubOrder>();
		final Iterator<Item> iteratorItemList = itemList.iterator();
		int itemListCounter = 0;
		final SubOrder pizzaSubOrder = new SubOrder();
		final SubOrder beverageSubOrder = new SubOrder();
		final SubOrder sidesSubOrder = new SubOrder();

		while (iteratorItemList.hasNext() && itemListCounter != itemList.size()) {
			final Item item = itemList.get(itemListCounter);
			final ItemType itemType = itemList.get(itemListCounter).getType();

			if (itemType.equals(ItemType.PIZZA)) {
				generateSuborder(pizzaSubOrder, item);
			} else if (itemType.equals(ItemType.BEVERAGE)) {
				generateSuborder(beverageSubOrder, item);
			} else if (itemType.equals(ItemType.SIDES)) {
				generateSuborder(sidesSubOrder, item);
			}
			itemListCounter++;
			iteratorItemList.next();
		}

		if(sidesSubOrder.getItems()!=null && sidesSubOrder.getQuantity()!=null)
				subOrderList.add(sidesSubOrder);
		
		if(beverageSubOrder.getItems()!=null && beverageSubOrder.getQuantity()!=null)
		subOrderList.add(beverageSubOrder);
		
		if(pizzaSubOrder.getItems()!=null && pizzaSubOrder.getQuantity()!=null)
		subOrderList.add(pizzaSubOrder);

		LOG.info("Sub-Orders created successfully");
		return subOrderList;
	}

	/**
	 * This method generates the suborder based on their type selected
	 * 
	 * @param Suborder
	 * @param item
	 *            This contains all the information about the suborder
	 */
	public SubOrder generateSuborder(final SubOrder subOrder, final Item item) {
		subOrder.setAmount(item.getPrice() + (subOrder.getAmount() != null ? subOrder.getAmount() : 0));
		subOrder.setQuantity(item.getQuantity() + (subOrder.getQuantity() != null ? subOrder.getQuantity() : 0));
		subOrder.setSubOrderType(item.getType());
		subOrder.addToItems(item);
		return subOrder;
	}

}
