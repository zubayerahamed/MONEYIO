package com.zayaanit.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.zayaanit.entity.Transaction;
import com.zayaanit.entity.TransactionDetail;
import com.zayaanit.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * 
 * @since May 9, 2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResDto {

	private Long id;
	private TransactionType type;
	private LocalDate xdate;
	private String xnote;
	List<TransactionDetailResDto> details;

	public static TransactionResDto convertToDto(Transaction transaction, TransactionDetail detail) {
		// Your conversion logic here
		TransactionResDto dto = new TransactionResDto();
		dto.setId(transaction.getId());
		dto.setType(transaction.getType());
		dto.setXdate(transaction.getXdate());
		dto.setXnote(transaction.getXnote());

		// set other fields
		TransactionDetailResDto detailDto = new TransactionDetailResDto();
		detailDto.setId(detail.getId());
		detailDto.setAccount(AccountResDto.convertToDto(detail.getAccount()));
		detailDto.setCategory(CategoryResDto.convertToDto(detail.getCategory()));
		detailDto.setSubCategory(SubCategoryResDto.convertToDto(detail.getSubCategory()));
		detailDto.setAmount(detail.getAmount());
		detailDto.setXsign(detail.getXsign());
		detailDto.setCharge(detail.isCharge());

		dto.setDetails(new ArrayList<>());
		dto.getDetails().add(detailDto);

		return dto;
	}

}
