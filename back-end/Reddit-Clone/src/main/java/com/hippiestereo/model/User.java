package com.hippiestereo.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank(message = "Username is required")
	private String username;
	
	@NotBlank(message = "password is required")
	private String password;
	
	@Email
	@NotEmpty(message = "Email is required")
	private String email;
	
	private Instant created;
	private boolean enabled;
}