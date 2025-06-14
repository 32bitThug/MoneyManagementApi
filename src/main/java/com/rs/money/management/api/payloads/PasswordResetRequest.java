package com.rs.money.management.api.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordResetRequest {
	@NotBlank(message="email required")
	private String email;
	@NotBlank(message = "new Password Required")
	private String newPassword;
}
