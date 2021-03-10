package com.hippiestereo.exceptions;

public class PostNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 1215452188436947368L;

	public PostNotFoundException(String message) {
		super(message);
    }
	
}
