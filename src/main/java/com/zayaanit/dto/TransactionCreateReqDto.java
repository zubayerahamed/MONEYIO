package com.zayaanit.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.zayaanit.enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * 
 * @since May 9, 2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateReqDto {

	@NotNull(message = "Type required")
	private TransactionType type;
	@NotNull(message = "Date required")
	private LocalDate xdate;
	private String xnote;
	@NotNull(message = "Account required")
	private Long accountId;
	@NotNull(message = "Category required")
	private Long categoryId;
	private Long subCategoryId;
	@NotNull(message = "Amount required")
	private BigDecimal amount;
	private int xsign;
	private boolean charge;
}
