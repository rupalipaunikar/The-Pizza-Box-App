package com.pizzaboxcore.router;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.constants.JUnitConstants;
import com.pizzabox.common.constants.PaymentType;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = JUnitConstants.LOCATION_APP_CONTEXT)
public class TestLocationRouter {

	@Autowired
	private LocationRouter locationRouter;
	
	@Test
	public void testErrorChannel(){
        Order order = new Order(1, PaymentType.CASH, Status.READY, null, 1000.0, null, null, null);
		String channel = locationRouter.route(order);
		Assert.assertEquals(Constants.ERROR_CHANNEL, channel);
	}
	
	@Test
	public void testNorthZoneChannel(){
		User user = new User();
        user.setAddress("Khadki");
        Order order = new Order(1, PaymentType.CASH, Status.READY, null, 1000.0, user, null, null);
		String channel = locationRouter.route(order);
		Assert.assertEquals(Constants.NORTH_ZONE_CHANNEL, channel);
	}
	
	@Test
	public void testSouthZoneChannel(){
		User user = new User();
        user.setAddress("Bibvewadi");
        Order order = new Order(1, PaymentType.CASH, Status.READY, null, 1000.0, user, null, null);
		String channel = locationRouter.route(order);
		Assert.assertEquals(Constants.SOUTH_ZONE_CHANNEL, channel);
	}
	
	@Test
	public void testEastZoneChannel(){
		User user = new User();
        user.setAddress("Hadapsar");
        Order order = new Order(1, PaymentType.CASH, Status.READY, null, 1000.0, user, null, null);
		String channel = locationRouter.route(order);
		Assert.assertEquals(Constants.EAST_ZONE_CHANNEL, channel);
	}
	
	@Test
	public void testWestZoneChannel(){
		User user = new User();
        user.setAddress("Bavdhan");
        Order order = new Order(1, PaymentType.CASH, Status.READY, null, 1000.0, user, null, null);
		String channel = locationRouter.route(order);
		Assert.assertEquals(Constants.WEST_ZONE_CHANNEL, channel);
	}
}
