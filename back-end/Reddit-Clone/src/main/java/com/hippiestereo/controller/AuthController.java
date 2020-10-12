package com.hippiestereo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hippiestereo.dto.AuthenticationResponseDTO;
import com.hippiestereo.dto.LoginRequestDTO;
import com.hippiestereo.dto.RegisterRequestDTO;
import com.hippiestereo.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequestDTO registerRequest) {
		authService.signup(registerRequest);
		
		return new ResponseEntity<String>("User Registration Successful", HttpStatus.OK);
	}
	
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token){
		authService.verifyAccount(token);
		
		return new ResponseEntity<String>("Account Activated Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponseDTO login(@RequestBody LoginRequestDTO loginRequest){
		return authService.login(loginRequest);
	}
}
