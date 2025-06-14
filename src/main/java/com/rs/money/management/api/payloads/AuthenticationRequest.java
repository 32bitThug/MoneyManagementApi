package com.rs.money.management.api.payloads;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

	private String email;
	private String password;

}
