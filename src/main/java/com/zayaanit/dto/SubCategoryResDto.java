package com.zayaanit.dto;

import com.zayaanit.entity.SubCategory;

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

	public static SubCategoryResDto convertToDto(SubCategory subCategory) {
		// Your conversion logic here
		SubCategoryResDto dto = new SubCategoryResDto();
		dto.setId(subCategory.getId());
		dto.setName(subCategory.getName());
		// set other fields
		return dto;
	}
}
