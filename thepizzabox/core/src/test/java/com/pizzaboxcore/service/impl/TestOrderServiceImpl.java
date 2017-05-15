package com.pizzaboxcore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzabox.common.constants.ItemType;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzaboxcore.custom.exception.DAOException;
import com.pizzaboxcore.custom.exception.OrderGenerationException;
import com.pizzaboxcore.custom.exception.UserNotFoundException;
import com.pizzaboxcore.dao.OrderDAO;
import com.pizzaboxcore.order.generator.OrderGenerator;
import com.pizzabox.common.constants.JUnitConstants;
import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * 
 * @author Roshni
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = JUnitConstants.LOCATION_APP_CONTEXT)
public class TestOrderServiceImpl {

	@Mock
	private OrderDAO orderDAO;

	@Mock
	private OrderGenerator orderGenerator;

	@InjectMocks
	private OrderServiceImpl orderService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGenerateOrder() throws Exception {

		System.out.println(JUnitConstants.ORDER.getId());
		
		final Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getName()).thenReturn(JUnitConstants.USERNAME);
		final SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		when(orderDAO.generateOrder(JUnitConstants.ORDER)).thenReturn(JUnitConstants.ORDER);
		when(orderDAO.getUserDetails(JUnitConstants.USERNAME)).thenReturn(JUnitConstants.USER);
		when(orderGenerator.generateOrder(JUnitConstants.FINAL_ITEM_LIST, JUnitConstants.USER))
				.thenReturn(JUnitConstants.ORDER);

		final Order order = orderService.generateOrder(JUnitConstants.FINAL_ITEM_LIST);

		assertNotNull(order);
		assertEquals(PaymentType.NOTSELECTED, order.getPaymentType());
		assertEquals(Status.SUBMITTED, order.getStatus());
		assertEquals(JUnitConstants.USERNAME, order.getUser().getUsername());

	}

	@Test
	public void testCalculateFinalOrderList() {

		final List<Integer> checkBoxList = new ArrayList<Integer>();
		checkBoxList.add(2);
		checkBoxList.add(3);

		final List<Item> finalOrderList = orderService.calculateFinalOrderList(JUnitConstants.ITEM_LIST, checkBoxList);
		Assert.assertNotNull(finalOrderList);
		Assert.assertSame(JUnitConstants.SELECTED_ITEM_NAME_ONE, finalOrderList.get(0).getName());
		Assert.assertSame(ItemType.BEVERAGE, finalOrderList.get(0).getType());
		Assert.assertSame(JUnitConstants.SELECTED_ITEM_NAME_TWO, finalOrderList.get(1).getName());
		Assert.assertSame(ItemType.SIDES, finalOrderList.get(1).getType());
	}

	@Test
	public void testGetUserDetails() throws Exception{
		
		when(orderDAO.getUserDetails(JUnitConstants.USERNAME)).thenReturn(JUnitConstants.USER);
		
		final User user = orderService.getUserDetails(JUnitConstants.USERNAME);
		
		Assert.assertNotNull(user);
		Assert.assertEquals(JUnitConstants.USERNAME, user.getUsername());
		Assert.assertEquals(JUnitConstants.USER.getFirstName(), user.getFirstName());
		Assert.assertEquals(JUnitConstants.USER.getLastName(), user.getLastName());
		Assert.assertEquals(JUnitConstants.USER.getRole(), user.getRole());
		Assert.assertEquals(JUnitConstants.USER.getContactNo(), user.getContactNo());
		Assert.assertEquals(JUnitConstants.USER.getAddress(), user.getAddress());
	}
}
