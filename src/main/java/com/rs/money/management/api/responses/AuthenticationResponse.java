package com.rs.money.management.api.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

	private String token;
	private Long userId;
	private Date expirationDate;
	private String error;
}
