package com.zayaanit.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * @since May 9, 2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailResDto {

	private Long id;
	private AccountResDto account;
	private CategoryResDto category;
	private SubCategoryResDto subCategory;
	private BigDecimal amount;
	private int xsign;
	private boolean charge;
}
