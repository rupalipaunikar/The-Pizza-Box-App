package com.pizzaboxcore.global.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.pizzaboxcore.constants.Constants;
import com.pizzaboxcore.custom.exception.NoItemFound;

/**
 * GlobalController is the controller taking care of global Exception handling
 * 
 * @author Roshni
 *
 */
@ControllerAdvice
public class GlobalController {
	

	/**
	 * This method takes care of the Exceptions thrown by Generic Exception Class
	 * 
	 * @param NoItemFound
	 * @param HttpsServletRequest
	 * @return ModelAndView 
	 */
	@ExceptionHandler(NoItemFound.class)
	public ModelAndView handleCustomException(final NoItemFound exception,final HttpServletRequest request) {

		ModelAndView model = new ModelAndView(Constants.ERROR_PAGE_URL);
		model.addObject(Constants.DETAIL_ERROR_MSG, exception.getMessage());
		model.addObject(Constants.EXCEPTION,exception);
		model.addObject(Constants.REQUESTED_URL,request.getRequestURI());
		return model;

	}
}
