package com.mescobar.helpdesk.exception;

public class DuplicateCPForEmailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateCPForEmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateCPForEmailException(String message) {
		super(message);
	}

}
