package com.pizzabox.common.constants;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.pizzabox.common.model.Item;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.SubOrder;
import com.pizzabox.common.model.User;
/**
 * 
 * @author Roshni
 *
 */
public class JUnitConstants {

	public static final String LOCATION_APP_CONTEXT = "file:src/main/webapp/WEB-INF/applicationContext.xml";
	public static final String LOCATION_DUMMY_ITEMS_LIST = "src/test/resources/dummyItemsList.txt";
	public static final String COMMA_SEPARATOR = ",";

	public static final String USERNAME = "Roshni";
	public static final Long FINAL_ORDER_LIST_SIZE = 2l;
	public static final String SELECTED_ITEM_NAME_ONE = "Pepsi";
	public static final String SELECTED_ITEM_NAME_TWO = "Pasta";

	public static final Double TOTAL_PRICE = 750.00;

	public static final List<Item> ITEM_LIST = new ArrayList<Item>() {
		{
			add(new Item(1, "Deluxe Veggie", 500.00, 1, ItemType.PIZZA));
			add(new Item(2, "Pepsi", 100.00, 1, ItemType.BEVERAGE));
			add(new Item(3, "Pasta", 200.00, 1, ItemType.SIDES));
		}
	};
	

	public static final List<Item> FINAL_ITEM_LIST = new ArrayList<Item>() {
		{
			add(new Item(2, "Pepsi", 100.00, 1, ItemType.BEVERAGE));
			add(new Item(3, "Pasta", 200.00, 1, ItemType.SIDES));
		}
	};

	public static final SubOrder PIZZA_SUBORDER = new SubOrder(1, ItemType.PIZZA, 1, 500.00);
	public static final SubOrder BEVERAGE_SUBORDER = new SubOrder(2, ItemType.BEVERAGE, 1, 100.00);
	public static final SubOrder SIDES_SUBORDER = new SubOrder(3, ItemType.SIDES, 1, 200.00);

	public static final List<SubOrder> SUBORDER_LIST = new ArrayList<SubOrder>() {
		{
			add(PIZZA_SUBORDER);
			add(BEVERAGE_SUBORDER);
			add(SIDES_SUBORDER);
		}
	};

	public static final User USER = new User(1,"Roshni","Swadia","Pune","9876546756","Roshni","Roshni","ROLE_GUEST");

	public static final Order ORDER = new Order(1, PaymentType.NOTSELECTED, Status.SUBMITTED, SUBORDER_LIST, TOTAL_PRICE,
			USER, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
	
	public static final Principal PRINCIPAL_OBJECT = new Principal() {
		public String getName() {
			return "Roshni";
		}
	};
	public static final SubOrder GENERATE_PIZZA_SUBORDER = new SubOrder(ItemType.PIZZA, 1, 500.00);
	public static final SubOrder GENERATE_BEVERAGE_SUBORDER = new SubOrder(ItemType.BEVERAGE, 1, 100.00);
	public static final SubOrder GENERATE_SIDES_SUBORDER = new SubOrder(ItemType.SIDES, 1, 200.00);
	
	public static final List<SubOrder> GENERATE_SUBORDER_LIST = new ArrayList<SubOrder>() {
		{
			add(GENERATE_PIZZA_SUBORDER);
			add(GENERATE_BEVERAGE_SUBORDER);
			add(GENERATE_SIDES_SUBORDER);
		}
	};
	
	public static final Order GENERATE_ORDER = new Order(PaymentType.NOTSELECTED, Status.SUBMITTED, GENERATE_SUBORDER_LIST, TOTAL_PRICE,
			USER, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
	
	
}
