package com.zayaanit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zayaanit.dto.AccountCreateReqDto;
import com.zayaanit.dto.AccountEditReqDto;
import com.zayaanit.dto.AccountResDto;
import com.zayaanit.entity.Account;
import com.zayaanit.exception.CustomException;
import com.zayaanit.model.PageResponse;
import com.zayaanit.repo.AccountRepo;

import jakarta.transaction.Transactional;

/**
 * Zubayer Ahamed May 8, 2026
 */
@Service
public class AccountService extends BaseService {

	@Autowired
	private AccountRepo accountRepo;


	@Transactional
	public AccountResDto create(AccountCreateReqDto reqDto) {

		Account account = Account.builder()
									.name(reqDto.getName())
									.excluded(reqDto.isExcluded())
									.user(loggedinUser())
									.build();

		// Check account exist with this name
		Optional<Account> accountOp = accountRepo.findByNameAndUser(account.getName(), account.getUser());
		if(accountOp.isPresent()) throw new CustomException("Account already exist", HttpStatus.BAD_REQUEST);

		account = accountRepo.save(account);

		return AccountResDto.convertToDto(account);
	}

	@Transactional
	public AccountResDto update(AccountEditReqDto reqDto) {
		// Check account exist with this name
		Optional<Account> accountOp = accountRepo.findById(reqDto.getId());
		if(!accountOp.isPresent()) throw new CustomException("Account not exist", HttpStatus.BAD_REQUEST);

		Account account = accountOp.get();
		account.setName(reqDto.getName());
		account.setExcluded(reqDto.isExcluded());
		account = accountRepo.save(account);

		return AccountResDto.convertToDto(account);
	}

	@Transactional
	public void deleteById(Long id) {
		// Check account exist with this name
		Optional<Account> accountOp = accountRepo.findById(id);
		if(!accountOp.isPresent()) throw new CustomException("Account not exist", HttpStatus.BAD_REQUEST);

		accountRepo.delete(accountOp.get());
	}

	public PageResponse<AccountResDto> getAll(int page, int size, String sortBy, String sortDir) {
		// Create sort object
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// Create pageable object
		Pageable pageable = PageRequest.of(page, size, sort);

		// Get page from repository
		Page<Account> accountPage = accountRepo.findAllByUser(loggedinUser(), pageable);

		// Convert to DTO page
		List<AccountResDto> content = accountPage.getContent().stream()
				.map(AccountResDto::convertToDto)
				.collect(Collectors.toList());

		// Return custom page response
		return new PageResponse<>(
			content, 
			accountPage.getNumber(), 
			accountPage.getSize(),
			accountPage.getTotalElements(), 
			accountPage.getTotalPages(), 
			accountPage.isFirst(),
			accountPage.isLast()
		);
	}



	public AccountResDto findById(@NonNull Long id) {
		Account account = accountRepo.findById(id).orElseThrow(() -> new CustomException("Account not exist", HttpStatus.NOT_FOUND));
		return AccountResDto.convertToDto(account);
	}

}
