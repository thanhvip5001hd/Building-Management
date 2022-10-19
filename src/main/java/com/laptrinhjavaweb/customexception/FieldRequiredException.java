package com.laptrinhjavaweb.customexception;

public class FieldRequiredException extends RuntimeException {
	
	public FieldRequiredException(String errorMessage) {
		super(errorMessage);
	}
}
