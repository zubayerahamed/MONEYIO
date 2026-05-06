package com.zayaanit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class AuthenticationReqDto {

	@NotBlank(message = "Email address required")
	@Size(min = 1, max = 100, message = "Email must be 1 to 100 characters long")
	private String email;
	@NotBlank(message = "Password required")
	private String password;
}
