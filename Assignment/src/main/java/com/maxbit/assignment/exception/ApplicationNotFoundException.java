package com.maxbit.assignment.exception;

public class ApplicationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationNotFoundException() {
        super();
    }

	public ApplicationNotFoundException(String customMessage) {
        super(customMessage);
    }
}