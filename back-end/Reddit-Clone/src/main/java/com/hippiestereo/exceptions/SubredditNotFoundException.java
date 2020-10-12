package com.hippiestereo.exceptions;

public class SubredditNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3966732309819621514L;

	public SubredditNotFoundException(String message) {
        super(message);
    }
	
}
