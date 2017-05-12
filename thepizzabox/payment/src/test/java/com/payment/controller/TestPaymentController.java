package com.payment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
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
import org.springframework.web.servlet.ModelAndView;

import com.payment.common.JUnitConstants;
import com.payment.data.Invoice;
import com.payment.data.PaymentDetails;
import com.payment.exception.PaymentProcessException;
import com.payment.exception.PaymentValidationException;
import com.payment.gateway.PaymentGateway;
import com.payment.validator.PaymentValidator;
import com.payment.validator.ValidationUtils;
import com.pizzabox.common.constants.Constants;
import com.pizzabox.common.model.CardDetails;
import com.pizzabox.common.model.Order;
import com.pizzabox.common.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { JUnitConstants.LOCATION_PAYMENT_CONTEXT, JUnitConstants.LOCATION_PAYMENT_FLOW })
public class TestPaymentController {

	@Mock
	private Model model;

	@Mock
	private PaymentGateway paymentGateway;

	@Mock
	private ValidationUtils validationUtils;

	@Mock
	private PaymentValidator paymentValidator;

	@Mock
	private HttpServletRequest httpServletRequest;
	
	@InjectMocks
	private PaymentController paymentController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
	}

	@Test
	public void testMakePayment() throws Exception {

		Mockito.when(validationUtils.getPaymentValidator()).thenReturn(paymentValidator);
		Mockito.doNothing().when(paymentValidator).validate(Mockito.any(Order.class), Mockito.any(User.class));
		
		mockMvc.perform(post("/makepayment").flashAttr(Constants.ORDER, new Order())
											.flashAttr(Constants.USER, new User()))
				.andExpect(forwardedUrl(Constants.PAYMENT_MODE))
				.andExpect(model().attributeExists(Constants.ORDER))
				.andExpect(model().attributeExists(Constants.USER))
				.andExpect(model().attributeExists(Constants.CARD_DETAILS));
	}
	
	@Test
	public void testInitatePaymentFlow() throws Exception {

		Mockito.when(validationUtils.getPaymentValidator()).thenReturn(paymentValidator);
		Mockito.when(paymentValidator.validate(Mockito.any(Order.class), Mockito.any(User.class),
											   Mockito.any(CardDetails.class))).thenReturn(Constants.NO_ERROR);
		
		Mockito.when(paymentGateway.makePayment(Mockito.any(PaymentDetails.class))).thenReturn(new Invoice());
		
		mockMvc.perform(post("/submit").flashAttr(Constants.ORDER, new Order())
									   .flashAttr(Constants.USER, new User())
									   .flashAttr(Constants.CARD_DETAILS, new CardDetails()))
				.andExpect(forwardedUrl(Constants.PAYMENT_RESULT))
				.andExpect(model().attributeExists(Constants.INVOICE));
	}
	
	@Test
	public void testInitatePaymentFlowForErrorScenarios() throws Exception {

		Mockito.when(validationUtils.getPaymentValidator()).thenReturn(paymentValidator);
		Mockito.when(paymentValidator.validate(Mockito.any(Order.class), Mockito.any(User.class),
											   Mockito.any(CardDetails.class))).thenReturn("some error");
		
		Mockito.when(paymentGateway.makePayment(Mockito.any(PaymentDetails.class))).thenReturn(new Invoice());
		
		mockMvc.perform(post("/submit").flashAttr(Constants.ORDER, new Order())
									   .flashAttr(Constants.USER, new User())
									   .flashAttr(Constants.CARD_DETAILS, new CardDetails()))
				.andExpect(forwardedUrl(Constants.PAYMENT_MODE))
				.andExpect(model().attributeExists(Constants.ERROR));
	}
	
	@Test
	public void testHandlePaymentValidationException(){
		StringBuffer url = new StringBuffer(JUnitConstants.URL);
		PaymentValidationException exception = new PaymentValidationException(Constants.EXCEPTION);
		
		Mockito.when(httpServletRequest.getRequestURL()).thenReturn(url);
		
		ModelAndView modelAndView = paymentController.handlePaymentValidationException(httpServletRequest, exception);
		
		Assert.assertEquals(Constants.ERROR, modelAndView.getViewName());
		Map<String, Object> modelMap = modelAndView.getModel();
		Assert.assertEquals(modelMap.get(Constants.EXCEPTION), exception);
		Assert.assertEquals(modelMap.get(Constants.URL), url);
	}
	
	@Test
	public void testHandlePaymentProcessException(){
		StringBuffer url = new StringBuffer(JUnitConstants.URL);
		PaymentProcessException exception = new PaymentProcessException(Constants.EXCEPTION);
		
		Mockito.when(httpServletRequest.getRequestURL()).thenReturn(url);
		
		ModelAndView modelAndView = paymentController.handlePaymentProcessException(httpServletRequest, exception);
		
		Assert.assertEquals(Constants.ERROR, modelAndView.getViewName());
		Map<String, Object> modelMap = modelAndView.getModel();
		Assert.assertEquals(modelMap.get(Constants.EXCEPTION), exception);
		Assert.assertEquals(modelMap.get(Constants.URL), url);
	}
}
