package com.payment.dao;

import com.payment.exception.DAOException;
import com.pizzabox.common.constants.Status;
import com.pizzabox.common.model.CardDetails;

/**
 * PaymentDAO contains the APIs to interact with CardDetails and Order table
 * 
 * @author rupalip
 *
 */
public interface PaymentDAO {

	/**
	 * This API retrieves the balance for a card
	 * 
	 * @param cardDetails
	 * @return balance
	 * @throws DAOException 
	 */
	Double getBalanceForCard(final CardDetails cardDetails) throws DAOException;
	
	/**
	 * This API updates the balance for a card
	 * 
	 * @param cardDetails
	 * @param balance
	 * @return no of rows affected
	 * @throws DAOException 
	 */
	Integer updateBalance(final CardDetails cardDetails, final Double balance) throws DAOException;
	
	/**
	 * This API updates the order status with the given status
	 * 
	 * @param orderId
	 * @param status
	 * @return no of rows affected
	 * @throws DAOException 
	 */
	Integer updateOrderStatus(final Integer orderId, final Status status) throws DAOException;
}
