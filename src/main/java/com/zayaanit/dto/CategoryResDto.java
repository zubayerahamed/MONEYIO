package com.zayaanit.dto;

import com.zayaanit.enums.CategoryType;

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
public class CategoryResDto {

	private Long id;
	private String name;
	private CategoryType type;
}
