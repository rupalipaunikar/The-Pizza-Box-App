package com.pizzaboxcore.router;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.model.Order;
import com.pizzaboxcore.constants.Zone;

@Component
public class LocationRouter {

	private static final Logger LOG = Logger.getLogger(LocationRouter.class);
	
	public String route(final Order order) {
		if(order == null || order.getUser() == null || Strings.isNullOrEmpty(order.getUser().getAddress())){
			final String errMsg = "Cannot route the order as either order or location information is unavailable";
			LOG.error(errMsg);
			return Constants.ERROR_CHANNEL;
		}
		
		final String address = order.getUser().getAddress();
		Zone zone = Zone.getZone(address);
		
		LOG.info("Routing order to "+zone.toString()+" office");
		return zone.getChannel();
	}
	
	
}
