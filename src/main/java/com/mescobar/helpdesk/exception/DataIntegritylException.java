package com.mescobar.helpdesk.exception;

public class DataIntegritylException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegritylException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegritylException(String message) {
		super(message);
	}

}
