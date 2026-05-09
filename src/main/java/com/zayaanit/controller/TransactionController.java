package com.zayaanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zayaanit.dto.TransactionCreateReqDto;
import com.zayaanit.dto.TransactionResDto;
import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.ReturnResponse;
import com.zayaanit.service.TransactionService;

import jakarta.validation.Valid;

/**
 * Zubayer Ahamed
 * 
 * @since May 8, 2026
 */
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;


	@PostMapping
	public ResponseEntity<ReturnResponse<TransactionResDto>> create(@Valid @RequestBody TransactionCreateReqDto reqDto) {
		TransactionResDto resData = transactionService.create(reqDto);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, resData);
	}

	
}
