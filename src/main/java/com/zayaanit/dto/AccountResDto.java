package com.zayaanit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed 
 * @since May 8, 2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResDto {

	private Long id;
	private String name;
	private boolean excluded;
}
