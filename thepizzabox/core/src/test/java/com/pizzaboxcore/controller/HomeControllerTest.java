package com.pizzaboxcore.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.pizzabox.common.constants.JUnitConstants;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;
import com.pizzabox.common.request.ItemWrapper;
import com.pizzaboxcore.service.ItemService;
import com.pizzaboxcore.service.OrderService;
import com.pizzaboxcore.validator.ItemValidator;
import com.pizzaboxcore.validator.ValidationUtils;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 
 * @author Roshni
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = JUnitConstants.LOCATION_APP_CONTEXT)
public class HomeControllerTest {


	@Mock
	private ItemService itemService;

	@Mock
	private OrderService orderService;

	@Mock
	private ValidationUtils validationUtils;
	
	@Mock
	private ItemValidator itemValidator;
	
	@InjectMocks
	private HomeController homecontroller;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(homecontroller).build();
	}

	@Test
	public void testShowHomePage() throws Exception {
		Mockito.when(itemService.createInitialList()).thenReturn(JUnitConstants.ITEM_LIST);
		
		Mockito.when(validationUtils.getItemValidator()).thenReturn(itemValidator);
		Mockito.doNothing().when(itemValidator).validateItemList(JUnitConstants.ITEM_LIST);
		
		mockMvc.perform(get("/homepage")).andExpect(forwardedUrl("userhomepage"))
				.andExpect(model().attributeExists("itemWrapper"));
	}

	@Test
	public void testMakeOrder() throws Exception {

		final ItemWrapper itemWrapper = new ItemWrapper();
		itemWrapper.setItemList(JUnitConstants.ITEM_LIST);

		final String[] checkBoxSelected = new String[] { "2", "3" };
		
		Mockito.when(validationUtils.getItemValidator()).thenReturn(itemValidator);
		Mockito.doNothing().when(itemValidator).validateItemWrapper(Mockito.any(ItemWrapper.class));
		
		when(orderService.calculateFinalOrderList(itemWrapper.getItemList(), getCheckBoxList(checkBoxSelected)))
				.thenReturn(JUnitConstants.FINAL_ITEM_LIST);
		when(orderService.generateOrder(JUnitConstants.FINAL_ITEM_LIST)).thenReturn(JUnitConstants.ORDER);

		mockMvc.perform(post("/makeOrder").param("itemCheckBox", checkBoxSelected).flashAttr("itemWrapper", itemWrapper))
				.andExpect(forwardedUrl("paymentgateway"))
		.andExpect(model().attributeExists("order")).andExpect(model().attributeExists("finalItemList"));
	}

	

	public List<Integer> getCheckBoxList(String[] checkBoxSelected) {

		final List<Integer> checkBoxList = new ArrayList<Integer>();
		for (int m = 0; m < checkBoxSelected.length; m++) {
			checkBoxList.add(Integer.parseInt(checkBoxSelected[m]));
		}
		return checkBoxList;
	}


}
