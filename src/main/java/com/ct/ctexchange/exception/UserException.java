package com.ct.ctexchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class UserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7692890596372888672L;

	public UserException(String message) {
		super(message);
	}

}
