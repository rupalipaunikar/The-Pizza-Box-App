package com.pizzaboxcore.order.generator;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.JUnitConstants;
import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.SubOrder;
import com.pizzaboxcore.order.generator.OrderGenerator;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations=JUnitConstants.LOCATION_APP_CONTEXT) 
public class TestOrderGenerator {

	@Autowired
	private OrderGenerator orderGenerator;

	@Test
	public void testCalculateTotalPrice() {

		Double totalPrice = orderGenerator.calculateTotalPrice(JUnitConstants.ITEM_LIST);

		Assert.assertEquals(new Double(800.00), new Double(totalPrice));
	}

	@Test
	public void testGenerateSubOrderList() {
		final List<SubOrder> subOrderList = orderGenerator.generateSubOrderList(JUnitConstants.ITEM_LIST);
		Assert.assertEquals(new Double(200.00), subOrderList.get(0).getAmount());
		Assert.assertEquals(new Double(100.00), subOrderList.get(1).getAmount());
		Assert.assertEquals(new Double(500.00), subOrderList.get(2).getAmount());

	}

	@Test
	public void testGenerateSuborder() {
		Item pizza = new Item();
		pizza.setItemId(1);
		pizza.setName("Deluxe Veggie");
		pizza.setPrice(500.00);
		pizza.setQuantity(1);
		pizza.setType(ItemType.PIZZA);

		SubOrder subOrder = new SubOrder();

		SubOrder subOrderReturned = orderGenerator.generateSuborder(subOrder, pizza);
		Assert.assertEquals(subOrder.getAmount(), subOrderReturned.getAmount());
		Assert.assertEquals(subOrder.getQuantity(), subOrderReturned.getQuantity());
		Assert.assertEquals(subOrder.getSubOrderType(), subOrderReturned.getSubOrderType());

	}
}
