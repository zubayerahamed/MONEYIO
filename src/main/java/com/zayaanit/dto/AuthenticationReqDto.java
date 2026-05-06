package com.zayaanit.dto;

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

	private String email;
	private String password;
}
