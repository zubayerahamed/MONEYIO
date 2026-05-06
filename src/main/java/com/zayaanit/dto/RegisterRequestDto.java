package com.zayaanit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * May 6, 2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

	@NotBlank(message = "First name required")
	private String firstName;
	private String lastName;
	@NotBlank(message = "Email address required")
	private String email;
	@NotBlank(message = "Password required")
	private String password;
}
