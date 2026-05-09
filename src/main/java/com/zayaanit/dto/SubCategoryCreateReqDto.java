package com.zayaanit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class SubCategoryCreateReqDto {

	@NotBlank(message = "Name required")
	@Size(min = 1, max = 50, message = "Name must be 1 to 50 characters long")
	private String name;

	@NotNull(message = "Category required")
	private Long categoryId;
}
