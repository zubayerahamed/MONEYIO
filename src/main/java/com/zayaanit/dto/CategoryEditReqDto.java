package com.zayaanit.dto;

import com.zayaanit.enums.CategoryType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CategoryEditReqDto {

	@NotNull(message = "Id required")
	private Long id;

	@NotBlank(message = "Name required")
	@Size(min = 1, max = 50, message = "Name must be 1 to 50 characters long")
	private String name;

	@NotNull(message = "Type required")
	private CategoryType type;
}
