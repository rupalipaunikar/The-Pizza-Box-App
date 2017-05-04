package com.pizzaboxcore.config;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import com.pizzaboxcore.constants.Constants;

/**
 * Listener class for log4j initialization
 * 
 * @author rupalip
 *
 */
public class Log4jListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		//do nothing
	}

	/**
	 * Initializes the log4j configuration
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter(Constants.LOG4J_CONFIG);
        String fullPath = context.getRealPath(Constants.EMPTY) + File.separator + log4jConfigFile;
         
        PropertyConfigurator.configure(fullPath);
	}
}
