package com.pizzabox.common.constants;

/**
 * This class holds system wide usable constants
 * 
 * @author rupalip
 *
 */
public class Constants {

	//config related
	public static final String LOG4J_CONFIG = "log4j-config-location";
	
	//common
	public static final String EMPTY = "";
	public static final String CHECKBOX_EMPTY = "No Items Selected. Please select the desired item to proceed further";
	public static final String ITEMLIST_NULL = "Item List not fetched from database";
	public static final String ITEMWRAPPER_NULL = "Item Wrapper or ItemList is not received in request";
	public static final String ERROR_PAGE_URL = "error";
	public static final String DETAIL_ERROR_MSG = "detailErrorMessage";
	public static final String REQUESTED_URL  = "url";
	public static final String EXCEPTION  = "exception";
	public static final String PRINCIPAL_OBJECT_NULL  = "Pricipal Object received from the request is null";
	public static final String USER_DETAILS_NULL = "No User Information received from the database";
	public static final String ORDER_GENERATED_NULL = "Order not generated for the user";
	public static final String ORDERLIST_GENERATED_NULL = "Order list not generated for the user";
	
	public static final String STATUS = "status";
	public static final String CARD_NUMBER = "cardNumber";
	public static final String EXPIRY_DATE = "expiryDate";
	public static final String CVV = "cvv";
	public static final String BALANCE = "balance";
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String INV = "INV";
	public static final String NO_ERROR = "No Error";
	
	//Model attributes
	public static final String ORDER = "order";
	public static final String USER = "user";
	public static final String URL = "url";
	public static final String CARD_DETAILS = "cardDetails";
	public static final String INVOICE = "invoice";
	
	//JSP related
	public static final String PAYMENT_MODE = "PaymentMode";
	public static final String PAYMENT_CASH = "PaymentCash";
	public static final String PAYMENT_SUCCESS = "PaymentSuccess";
	public static final String PAYMENT_FAILURE = "PaymentFailure";
	public static final String SOMETHING_WENT_WRONG = "SomethingWentWrong";
	public static final String PAYMENT_RESULT = "PaymentResult";
	public static final String ERROR = "Error";
	
	//payment-flow.xml related
	public static final String CASH_CHANNEL = "cashChannel";
	public static final String ONLINE_CHANNEL = "onlineChannel";
	
}
