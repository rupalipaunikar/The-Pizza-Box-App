package com.pizzaboxcore.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.service.OrderService;
import com.pizzabox.common.constants.JUnitConstants;
import org.junit.Assert;
/**
 * 
 * @author Roshni
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations=JUnitConstants.LOCATION_APP_CONTEXT)  
public class TestOrderServiceImpl {

	@Autowired
	private OrderService orderService;
	
	@Test
	public void testGenerateOrder() {
		
		User user = new User();
		user.setAddress("Pune");
		user.setContactNo("565757");
		user.setFirstName("Roshni");
		user.setLastName("Swadia");
		user.setRole("ROLE_GUEST");
		user.setUsername("Roshni");
		user.setPassword("Roshni");
		
		Item pizza = new Item();
		pizza.setName("Deluxe Veggie");
		pizza.setPrice(500.00);
		pizza.setQuantity(1);
		pizza.setType(ItemType.PIZZA);
		
		Item beverage = new Item();
		pizza.setName("Pepsi");
		pizza.setPrice(50.00);
		pizza.setQuantity(1);
		pizza.setType(ItemType.BEVERAGE);
		
		Item sides = new Item();
		pizza.setName("Pasta");
		pizza.setPrice(200.00);
		pizza.setQuantity(1);
		pizza.setType(ItemType.SIDES);
		
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(pizza);
		itemList.add(beverage);
		itemList.add(sides);
		
		Double totalPrice = 750.00;

		Order order = orderService.generateOrder(itemList, totalPrice, user);
		
		Assert.assertNotNull(order);
		Assert.assertEquals(PaymentType.NOTSELECTED,order.getPaymentType());
		Assert.assertEquals( Status.SUBMITTED,order.getStatus());
		Assert.assertEquals( "Roshni",order.getUser().getUsername());
		Assert.assertEquals(3, order.getSubOrders().size());
		
		// TODO Auto-generated method stub
	}

	@Test
	public void getUserDetails() {
		// TODO Auto-generated method stub
	}

}
