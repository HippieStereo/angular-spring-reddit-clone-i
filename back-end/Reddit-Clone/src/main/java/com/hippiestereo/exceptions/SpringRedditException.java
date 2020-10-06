package com.hippiestereo.exceptions;

public class SpringRedditException extends RuntimeException{
	private static final long serialVersionUID = 1538960756566929135L;

	public SpringRedditException(String exMessage, Exception exception) {
		super(exMessage, exception);
	}
	
	public SpringRedditException(String exMessage) {
		super(exMessage);
	}
}
