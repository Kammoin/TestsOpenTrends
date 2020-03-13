package com.opentrends.repository.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom validation messages on controller input layer.
 * 
 * @author iorodriguez
 *
 */
@ControllerAdvice
public class CustomValidationExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ERRORS = "errors";
	private static final String STATUS = "status";
	private static final String TIMESTAMP = "timestamp";

	/**
	 *  Error handle for @Valid
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, new Date());
		body.put(STATUS, status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put(ERRORS, errors);

		return new ResponseEntity<>(body, headers, status);

	}

}
