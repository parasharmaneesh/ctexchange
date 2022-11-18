package com.ct.ctexchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class TransactionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2215991992966293830L;

	public TransactionException(String message) {
		super(message);
	}

}
