package com.hippiestereo.exceptions;

public class UsernameNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -6547249075407761432L;

	public UsernameNotFoundException(String message) {
		super(message);
	}
	
}
