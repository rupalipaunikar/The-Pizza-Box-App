package com.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.dao.PaymentDAO;
import com.payment.data.PaymentDetails;
import com.payment.service.PaymentService;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.model.CardDetails;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDAO paymentDAO;
	
	@Override
	public String executePayment(PaymentDetails paymentDetails) {
		CardDetails cardDetails = paymentDetails.getCardDetails();
		Double totalAmount = paymentDetails.getTotalAmount();
		
		Double balance = paymentDAO.getBalanceForCard(cardDetails);
		
		
		if(balance >= totalAmount){
			balance -= totalAmount;
			int result = paymentDAO.updateBalance(cardDetails, balance);
			if(result != 1){
				//throw exception
			}
			return Constants.SUCCESS;
		}
		else{
			return Constants.FAILURE;
		}
	}

}
