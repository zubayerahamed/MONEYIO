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

import com.zayaanit.dto.SubCategoryCreateReqDto;
import com.zayaanit.dto.SubCategoryEditReqDto;
import com.zayaanit.dto.SubCategoryResDto;
import com.zayaanit.entity.Category;
import com.zayaanit.entity.SubCategory;
import com.zayaanit.exception.CustomException;
import com.zayaanit.model.PageResponse;
import com.zayaanit.repo.CategoryRepo;
import com.zayaanit.repo.SubCategoryRepo;

import jakarta.transaction.Transactional;

/**
 * Zubayer Ahamed May 8, 2026
 */
@Service
public class SubCategoryService extends BaseService {

	@Autowired
	private SubCategoryRepo subCategoryRepo;
	@Autowired
	private CategoryRepo categoryRepo;


	@Transactional
	public SubCategoryResDto create(SubCategoryCreateReqDto reqDto) {

		// Get category from db first
		Optional<Category> categoryOp = categoryRepo.findById(reqDto.getCategoryId());
		if(!categoryOp.isPresent()) throw new CustomException("Parent category not found", HttpStatus.NOT_FOUND);

		SubCategory subCategory = SubCategory.builder()
									.name(reqDto.getName())
									.user(loggedinUser())
									.category(categoryOp.get())
									.build();

		// Check SubCategory exist with this name
		Optional<SubCategory> subCategoryOp = subCategoryRepo.findByNameAndCategoryAndUser(subCategory.getName(), subCategory.getCategory(), subCategory.getUser());
		if(subCategoryOp.isPresent()) throw new CustomException("Sub Category already exist", HttpStatus.BAD_REQUEST);

		subCategory = subCategoryRepo.save(subCategory);

		return SubCategoryResDto.convertToDto(subCategory);
	}

	@Transactional
	public SubCategoryResDto update(SubCategoryEditReqDto reqDto) {
		// Get category from db first
		Optional<Category> categoryOp = categoryRepo.findById(reqDto.getCategoryId());
		if(!categoryOp.isPresent()) throw new CustomException("Parent category not found", HttpStatus.NOT_FOUND);


		// Check SubCategory exist with this name
		Optional<SubCategory> subCategoryOp = subCategoryRepo.findById(reqDto.getId());
		if(!subCategoryOp.isPresent()) throw new CustomException("Sub Category not exist", HttpStatus.BAD_REQUEST);

		SubCategory subCategory = subCategoryOp.get();
		subCategory.setName(reqDto.getName());
		subCategory.setCategory(categoryOp.get());
		subCategory = subCategoryRepo.save(subCategory);

		return SubCategoryResDto.convertToDto(subCategory);
	}

	@Transactional
	public void deleteById(Long id) {
		// Check SubCategory exist with this name
		Optional<SubCategory> subCategoryOp = subCategoryRepo.findById(id);
		if(!subCategoryOp.isPresent()) throw new CustomException("Sub Category not exist", HttpStatus.NOT_FOUND);

		subCategoryRepo.delete(subCategoryOp.get());
	}

	public PageResponse<SubCategoryResDto> getAll(Long categoryId, int page, int size, String sortBy, String sortDir) {
		// Get category from db first
		Optional<Category> categoryOp = categoryRepo.findById(categoryId);
		if(!categoryOp.isPresent()) throw new CustomException("Parent category not found", HttpStatus.NOT_FOUND);

		// Create sort object
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// Create pageable object
		Pageable pageable = PageRequest.of(page, size, sort);

		// Get page from repository
		Page<SubCategory> subCategoryPage = subCategoryRepo.findAllByCategoryAndUser(categoryOp.get(), loggedinUser(), pageable);

		// Convert to DTO page
		List<SubCategoryResDto> content = subCategoryPage.getContent().stream()
				.map(SubCategoryResDto::convertToDto)
				.collect(Collectors.toList());

		// Return custom page response
		return new PageResponse<>(
			content, 
			subCategoryPage.getNumber(), 
			subCategoryPage.getSize(),
			subCategoryPage.getTotalElements(), 
			subCategoryPage.getTotalPages(), 
			subCategoryPage.isFirst(),
			subCategoryPage.isLast()
		);
	}

	public SubCategoryResDto findById(@NonNull Long id) {
		SubCategory subCategory = subCategoryRepo.findById(id).orElseThrow(() -> new CustomException("Sub Category not exist", HttpStatus.NOT_FOUND));
		return SubCategoryResDto.convertToDto(subCategory);
	}

}
