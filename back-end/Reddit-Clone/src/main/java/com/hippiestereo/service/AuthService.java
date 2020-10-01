package com.hippiestereo.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippiestereo.dto.RegisterRequest;
import com.hippiestereo.model.User;
import com.hippiestereo.model.VerificationToken;
import com.hippiestereo.repository.UserRepository;
import com.hippiestereo.repository.VerificationTokenRepository;

@Service
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepository;
	private final UserRepository userRepository;
	
	public AuthService(PasswordEncoder passwordEncoder, 
			UserRepository userRepository, 
			VerificationTokenRepository verificationTokenRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
	}
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		
		user.setEmail(registerRequest.getEmail());
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		
		return token;
	}
}
