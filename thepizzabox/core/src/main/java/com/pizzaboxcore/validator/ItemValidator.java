package com.pizzaboxcore.validator;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.pizzabox.common.model.Item;
import com.pizzabox.common.request.ItemWrapper;
import com.pizzaboxcore.constants.Constants;
import com.pizzaboxcore.custom.exception.NoItemFound;
import com.pizzaboxcore.service.impl.OrderServiceImpl;

@Component
public class ItemValidator {
	private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class);

	public void validateItemList(final List<Item> itemList) throws NoItemFound{
		if(itemList==null){
			LOG.info(Constants.ITEMLIST_NULL);
			throw new NoItemFound(Constants.ITEMLIST_NULL);
		}
	}

	public void validateItemWrapper(ItemWrapper itemWrapper) throws NoItemFound {
		if(itemWrapper==null || itemWrapper.getItemList()==null ){
			LOG.info(Constants.ITEMWRAPPER_NULL);
			throw new NoItemFound(Constants.ITEMWRAPPER_NULL);
		}
	}
	
	
	
}
