package com.opentrends.ci.config;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for SwaggerConfig
 * 
 * @author iorodriguez
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * Auth hardcore for tests.
	 * @return
	 */
	@Bean
	public Docket api() {

		// Adding Header
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("Authorization") // name of header
				.modelRef(new ModelRef("string")).parameterType("header") // type - header
				.defaultValue("Basic YWRtaW46cGFzc3dvcmQ=") // based64 of - admin:password
				.required(true) // for compulsory
				.build();
		
		java.util.List<Parameter> aParameters = new ArrayList<>();
		aParameters.add(aParameterBuilder.build()); // add parameter

		return new Docket(SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.opentrends.ci.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo()).globalOperationParameters(aParameters);
	}

	/**
	 * Some dummy API Info
	 * 
	 * @return {@link ApiInfo}
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo("My ContinuousIntegrationService API", "Some custom description of API.", "API TOS", "Terms of service",
				new Contact("Iván Pérez", "www.example.com", "vantod@gmail.com"), "License of API", "API license URL",
				Collections.emptyList());
	}
}
