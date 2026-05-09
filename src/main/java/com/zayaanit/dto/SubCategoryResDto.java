package com.zayaanit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * @since May 9, 2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResDto {

	private Long id;
	private String name;
}
