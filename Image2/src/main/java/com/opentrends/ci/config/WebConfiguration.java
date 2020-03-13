package com.opentrends.ci.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * To allow h2-console view in docker
 * @author iorodriguez
 *
 */
@Configuration
public class WebConfiguration {

	@Bean
	ServletRegistrationBean<WebServlet> h2servletRegistration() {
		ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<WebServlet>(new org.h2.server.web.WebServlet());
		registration.addUrlMappings("/h2-console/*");
		registration.addInitParameter("webAllowOthers", "true");

		return registration;
	}

}
