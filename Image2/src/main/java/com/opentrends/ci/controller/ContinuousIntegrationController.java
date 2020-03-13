package com.opentrends.ci.controller;

import java.util.SplittableRandom;

import javax.validation.Valid;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.opentrends.ci.bean.Build;
import com.opentrends.ci.service.ContinuousIntegrationService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for CI
 * 
 * @author iorodriguez
 *
 */
@RestController
@Slf4j
public class ContinuousIntegrationController {

	/**
	 * MDC for trace logs.
	 */
	private static final String REQUEST_ID = "request_id";

	/**
	 * Business logic for CI actions.
	 */
	@Autowired
	ContinuousIntegrationService continuousIntService;

	/**
	 * Messages properties
	 */
	@Autowired
	MessageSource messageSource;

	/**
	 * Creates a new build from specifications.
	 * 
	 * @param buildSpec
	 *            Build spectifications.
	 * 
	 */
	@PostMapping(path = "/ci/build")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> newBuild(@Valid @RequestBody Build buildSpec) {

		// XXX: It could have been created an aspect to avoid boilerplate
		// writing logs at start and end of methods in controller class,
		// but I like to custom the input and exit log for every method in
		// controller

		// XXX: To simplify, I added a basic auth with spring security and
		// setted by default in Swagger api.

		// XXX: MDC- To simplify, I added a MDC random in logs to trace
		// purposes. Maybe this service has not a huge traffic but I put for
		// demo.
		// In a real scenario we could use, for example, a user id extracted
		// from request and add MDC logic inside aspect class.

		SplittableRandom splittableRandom = new SplittableRandom();
		MDC.put(REQUEST_ID, String.valueOf(splittableRandom.nextInt(0, 100)));

		log.info(">>>::newBuild::{}", buildSpec.toString());

		HttpStatus httpStatus = continuousIntService.build(buildSpec);

		log.info("<<<::newBuild::Finished with http status::{}", httpStatus);

		String returnMessage = (HttpStatus.OK == httpStatus)
				? messageSource.getMessage("ci.build.scheduled.success", null, null)
				: messageSource.getMessage("ci.build.scheduled.error", null, null);

		return ResponseEntity.status(httpStatus).body(returnMessage);

	}

	// XXX: To simplify the app, I have not created the entire CRUD for
	// repositories.

}
