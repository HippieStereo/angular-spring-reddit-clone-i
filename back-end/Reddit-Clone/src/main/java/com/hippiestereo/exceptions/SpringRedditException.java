package com.hippiestereo.exceptions;

public class SpringRedditException extends RuntimeException{
	private static final long serialVersionUID = 1538960756566929135L;

	public SpringRedditException(String message, Exception exception) {
		super(message, exception);
	}
	
	public SpringRedditException(String message) {
		super(message);
	}
}
