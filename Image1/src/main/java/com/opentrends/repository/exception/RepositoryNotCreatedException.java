package com.opentrends.repository.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception with 500 error hardcoded.
 * 
 * @author iorodriguez
 *
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class RepositoryNotCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param errorMessage
	 */
	public RepositoryNotCreatedException(String errorMessage) {
		super(errorMessage);

	}

}
