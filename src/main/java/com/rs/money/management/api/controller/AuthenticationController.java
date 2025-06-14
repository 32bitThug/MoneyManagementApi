package com.rs.money.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rs.money.management.api.payloads.AuthenticationRequest;
import com.rs.money.management.api.payloads.PasswordResetRequest;
import com.rs.money.management.api.payloads.RegisterRequest;
import com.rs.money.management.api.responses.AuthenticationResponse;
import com.rs.money.management.api.services.impl.AuthenticationService;

import jakarta.validation.Valid;

@RequestMapping(" ")
@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		AuthenticationResponse response=	authenticationService.register(request);
		if(response.getError()!=null) {
			return ResponseEntity.status(409).body(response);
		} 
			return ResponseEntity.ok(response);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
	@PatchMapping("/forgot-password")
	public String resetPassword(@Valid @RequestBody PasswordResetRequest request) {
		return authenticationService.resetPassword(request);
	}
}
