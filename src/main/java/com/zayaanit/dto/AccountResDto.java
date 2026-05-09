package com.zayaanit.dto;

import com.zayaanit.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * 
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

	public static AccountResDto convertToDto(Account account) {
		// Your conversion logic here
		AccountResDto dto = new AccountResDto();
		dto.setId(account.getId());
		dto.setName(account.getName());
		dto.setExcluded(account.isExcluded());
		// set other fields
		return dto;
	}
}
