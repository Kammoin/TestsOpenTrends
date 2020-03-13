package com.opentrends.ci.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration class for Messages.
 * 
 * @author iorodriguez
 *
 */
@Configuration
public class MessagesConfig {

	@Bean
	public ResourceBundleMessageSource messageSource() {

		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("i18n/messages");
		source.setUseCodeAsDefaultMessage(true);
		source.setDefaultEncoding("UTF-8");

		return source;
	}

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

}
