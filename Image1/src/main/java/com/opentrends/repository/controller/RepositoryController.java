package com.opentrends.repository.controller;

import java.util.Optional;
import java.util.SplittableRandom;

import javax.validation.Valid;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.opentrends.repository.bean.RepositorySpec;
import com.opentrends.repository.exception.RepositoryNotCreatedException;
import com.opentrends.repository.factory.RepositoryType;
import com.opentrends.repository.service.RepositoryService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for repository
 * 
 * @author iorodriguez
 *
 */
@RestController
@Slf4j
public class RepositoryController {

	/**
	 * MDC for trace logs.
	 */
	private static final String REQUEST_ID = "request_id";

	/**
	 * Business logic for repository actions.
	 */
	@Autowired
	RepositoryService repositoryService;

	@Autowired
	MessageSource messageSource;

	/**
	 * Creates a new repository from specifications.
	 * 
	 * @param repositorySpec
	 *            Repository spectifications.
	 * @param repositoryType
	 *            Type of repository
	 * 
	 * @return {@link String} Url of new repository.
	 * 
	 */
	@PostMapping(path = "/repository/{repositoryType}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> newRepository(@Valid @RequestBody RepositorySpec repositorySpec,
			@PathVariable("repositoryType") RepositoryType repositoryType) {

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

		log.info(">>>::newRepository::{}", repositorySpec.toString());

		Optional<String> webUrlRepo = repositoryService.initRepository(repositorySpec, repositoryType);

		if (webUrlRepo.isPresent())
			log.info("<<<::newRepository::Web Url Repo {}", webUrlRepo.get());

		return ResponseEntity.status(HttpStatus.OK).body(webUrlRepo.orElseThrow(() -> new RepositoryNotCreatedException(
				messageSource.getMessage("repository.internal.server.error", null, null))));

	}

	// XXX: To simplify the app, I have not created the entire CRUD for
	// repositories.

}
