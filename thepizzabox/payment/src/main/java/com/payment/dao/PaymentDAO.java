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
	 * @throws DAOException 
	 */
	void updateBalance(final CardDetails cardDetails) throws DAOException;
	
	/**
	 * This API updates the order status with the given status
	 * 
	 * @param orderId
	 * @param status
	 * @throws DAOException 
	 */
	void updateOrderStatus(final Integer orderId, final Status status) throws DAOException;
}
