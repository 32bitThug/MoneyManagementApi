package com.rs.money.management.api.services.impl;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rs.money.management.api.entities.User;
import com.rs.money.management.api.enums.Roles;
import com.rs.money.management.api.jwt.JwtServices;
import com.rs.money.management.api.payloads.AuthenticationRequest;
import com.rs.money.management.api.payloads.PasswordResetRequest;
import com.rs.money.management.api.payloads.RegisterRequest;
import com.rs.money.management.api.repository.UserRepo;
import com.rs.money.management.api.responses.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepo userRepo;
	private final JwtServices jwtService;
	private final AuthenticationManager authenticationManager;
	//PasswordEncoder interface is used to encode and verify passwords securely.
	// //The .encode() method is used to hash a raw password before storing it, typically in a database.
	private final PasswordEncoder passwordEncoder;

	public AuthenticationResponse register( RegisterRequest request) {
		if (userRepo.existsByEmail(request.getEmail()))	{
			return (AuthenticationResponse.builder().error("Email already exists!").build());
        }
		User user = User.createUser(request.getFullName(), request.getEmail(),
				passwordEncoder.encode(request.getPassword()), Roles.User);
		
		userRepo.save(user);
//		 creates a token which has expiration time and time which it was encoded
		String jwtToken = jwtService.generateToken(user);

		 return (AuthenticationResponse.builder().token(jwtToken).userId(user.getUserId())
				.expirationDate(jwtService.extractExpiration(jwtToken)).build());
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {


		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepo.findByEmail(request.getEmail()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder().token(jwtToken).userId(user.getUserId())
				.expirationDate(jwtService.extractExpiration(jwtToken)).build();
	}
	public String resetPassword(PasswordResetRequest request) {
		if (!userRepo.existsByEmail(request.getEmail()))	{
			return "Email Doesnt Exist";
        }
		User user = userRepo.findByEmail(request.getEmail()).orElseThrow();
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepo.save(user);
		return "hi";
	}
}
