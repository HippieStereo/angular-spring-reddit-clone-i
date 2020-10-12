package com.hippiestereo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippiestereo.dto.AuthenticationResponseDTO;
import com.hippiestereo.dto.LoginRequestDTO;
import com.hippiestereo.dto.RegisterRequestDTO;
import com.hippiestereo.exceptions.SpringRedditException;
import com.hippiestereo.exceptions.UsernameNotFoundException;
import com.hippiestereo.model.NotificationEmail;
import com.hippiestereo.model.User;
import com.hippiestereo.model.VerificationToken;
import com.hippiestereo.repository.UserRepository;
import com.hippiestereo.repository.VerificationTokenRepository;
import com.hippiestereo.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepository;
	private final UserRepository userRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	//private final RefreshTokenService refreshTokenService;
	
	@Transactional
	public void signup(RegisterRequestDTO registerRequest) {
		
		User user = new User();
		
		user.setEmail(registerRequest.getEmail());
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
		
		mailService.sendmail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
	}
	
    @Transactional(readOnly = true)
    public User getCurrentUser() {
    	
        org.springframework.security.core.userdetails.User principal
        		= (org.springframework.security.core.userdetails.User) SecurityContextHolder
        		.getContext().getAuthentication().getPrincipal();
        
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
        
    }

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		
		return token;
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		
		fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token !!!")));
	}

	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new SpringRedditException("User not found with name - " + username));
		user.setEnabled(true);
		
		userRepository.save(user);
	}

	public AuthenticationResponseDTO login(LoginRequestDTO loginRequest) {
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		String token = jwtProvider.generateToken(authenticate);
		
		return new AuthenticationResponseDTO(token, loginRequest.getUsername());
		
	}
	
    public boolean isLoggedIn() {
    	
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
        
    }
	
}
