package com.zayaanit.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zayaanit.dto.AccountResDto;
import com.zayaanit.dto.CategoryResDto;
import com.zayaanit.dto.SubCategoryResDto;
import com.zayaanit.dto.TransactionCreateReqDto;
import com.zayaanit.dto.TransactionDetailResDto;
import com.zayaanit.dto.TransactionResDto;
import com.zayaanit.entity.Account;
import com.zayaanit.entity.Category;
import com.zayaanit.entity.SubCategory;
import com.zayaanit.entity.Transaction;
import com.zayaanit.entity.TransactionDetail;
import com.zayaanit.enums.TransactionType;
import com.zayaanit.exception.CustomException;
import com.zayaanit.repo.AccountRepo;
import com.zayaanit.repo.CategoryRepo;
import com.zayaanit.repo.SubCategoryRepo;
import com.zayaanit.repo.TransactionDetailRepo;
import com.zayaanit.repo.TransactionRepo;

import jakarta.transaction.Transactional;

/**
 * Zubayer Ahamed
 * @since May 9, 2026
 */
@Service
public class TransactionService extends BaseService {

	@Autowired private TransactionRepo transactionRepo;
	@Autowired private TransactionDetailRepo transactionDetailRepo;
	@Autowired private AccountRepo accountRepo;
	@Autowired private CategoryRepo categoryRepo;
	@Autowired private SubCategoryRepo subCategoryRepo;

	// OPENING, INCOME, EXPENSE
	@Transactional
	public TransactionResDto create(TransactionCreateReqDto reqDto) {
		int xsign = 1;
		if(reqDto.getType().equals(TransactionType.EXPENSE)) {
			xsign = -1;
		}

		// Check account
		Optional<Account> accountOp = accountRepo.findById(reqDto.getAccountId());
		if(!accountOp.isPresent()) throw new CustomException("Account not exist", HttpStatus.NOT_FOUND);

		// Check category
		Optional<Category> categoryOp = categoryRepo.findById(reqDto.getCategoryId());
		if(!categoryOp.isPresent()) throw new CustomException("Category not exist", HttpStatus.NOT_FOUND);

		// Check sub category if id provided
		Optional<SubCategory> subCategoryOp = subCategoryRepo.findById(reqDto.getSubCategoryId());
		if(reqDto.getSubCategoryId() != null && !subCategoryOp.isPresent()) throw new CustomException("Sub Category not exist", HttpStatus.NOT_FOUND);

		// Create transaction
		Transaction transaction = Transaction.builder()
								.type(reqDto.getType())
								.xdate(reqDto.getXdate())
								.xnote(reqDto.getXnote())
								.user(loggedinUser())
								.build();

		transaction = transactionRepo.save(transaction);

		// Create transaction detail
		TransactionDetail detail = new TransactionDetail();
		detail.setTransaction(transaction);
		detail.setAccount(accountOp.get());
		detail.setCategory(categoryOp.get());
		if(reqDto.getSubCategoryId() != null) {
			detail.setSubCategory(subCategoryOp.get());
		}
		detail.setAmount(reqDto.getAmount());
		detail.setXsign(xsign);
		detail.setCharge(reqDto.isCharge());
		detail = transactionDetailRepo.save(detail);

		return TransactionResDto.convertToDto(transaction, detail);
	}
	
	
	
	
	
	
	
	
}
