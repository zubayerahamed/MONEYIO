package com.zayaanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zayaanit.dto.AccountCreateReqDto;
import com.zayaanit.dto.AccountEditReqDto;
import com.zayaanit.dto.AccountResDto;
import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.PageResponse;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.ReturnResponse;
import com.zayaanit.service.AccountService;

import jakarta.validation.Valid;

/**
 * Zubayer Ahamed
 * 
 * @since May 8, 2026
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping
	public ResponseEntity<ReturnResponse<PageResponse<AccountResDto>>> getAll(
		@RequestParam(defaultValue = "0") int page, 
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy, 
		@RequestParam(defaultValue = "asc") String sortDir) 
	{
		PageResponse<AccountResDto> resData = accountService.getAll(page, size, sortBy, sortDir);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReturnResponse<AccountResDto>> findById(@PathVariable Long id) {
		AccountResDto resData = accountService.findById(id);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	}

	@PostMapping
	public ResponseEntity<ReturnResponse<AccountResDto>> create(@Valid @RequestBody AccountCreateReqDto reqDto) {
		AccountResDto resData = accountService.create(reqDto);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, resData);
	}

	@PutMapping
	public ResponseEntity<ReturnResponse<AccountResDto>> update(@Valid @RequestBody AccountEditReqDto reqDto) {
		AccountResDto resData = accountService.update(reqDto);
		return ResponseBuilder.build(ResponseStatusType.UPDATE_SUCCESS, resData);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ReturnResponse<AccountResDto>> delete(@PathVariable Long id) {
		accountService.deleteById(id);
		return ResponseBuilder.build(ResponseStatusType.DELETE_SUCCESS, null);
	}
}
