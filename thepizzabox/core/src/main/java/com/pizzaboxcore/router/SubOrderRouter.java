package com.pizzaboxcore.router;

import java.util.ArrayList;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.router.AbstractMessageRouter;

import com.pizzabox.common.constants.ItemType;
import com.pizzaboxcore.model.CompleteOrder;

/**
 * 
 * @author Roshni
 */

public class SubOrderRouter extends AbstractMessageRouter {

	@Autowired
	@Qualifier("pizzaProcessChannel")
	private MessageChannel pizzaProcessChannel;

	@Autowired
	@Qualifier("sidesProcessChannel")
	private MessageChannel sidesProcessChannel;

	@Autowired
	@Qualifier("beverageProcessChannel")
	private MessageChannel beverageProcessChannel;

	@Override
	protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {

		Collection<MessageChannel> targetMessageChannel = new ArrayList<MessageChannel>();
		CompleteOrder completeSubOrder = (CompleteOrder) message.getPayload();

		if (completeSubOrder.getSubOrderType().equals(ItemType.PIZZA)) {
			targetMessageChannel.add(pizzaProcessChannel);
		} else if (completeSubOrder.getSubOrderType().equals(ItemType.SIDES)) {
			targetMessageChannel.add(sidesProcessChannel);
		} else if (completeSubOrder.getSubOrderType().equals(ItemType.BEVERAGE)) {
			targetMessageChannel.add(beverageProcessChannel);
		}

		return targetMessageChannel;
	}

}
