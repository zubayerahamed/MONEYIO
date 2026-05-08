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

import com.zayaanit.dto.CategoryCreateReqDto;
import com.zayaanit.dto.CategoryEditReqDto;
import com.zayaanit.dto.CategoryResDto;
import com.zayaanit.entity.Category;
import com.zayaanit.exception.CustomException;
import com.zayaanit.model.PageResponse;
import com.zayaanit.repo.CategoryRepo;

import jakarta.transaction.Transactional;

/**
 * Zubayer Ahamed May 8, 2026
 */
@Service
public class CategoryService extends BaseService {

	@Autowired
	private CategoryRepo categoryRepo;


	@Transactional
	public CategoryResDto create(CategoryCreateReqDto reqDto) {

		Category category = Category.builder()
									.name(reqDto.getName())
									.type(reqDto.getType())
									.user(loggedinUser())
									.build();

		// Check category exist with this name
		Optional<Category> categoryOp = categoryRepo.findByNameAndUser(category.getName(), category.getUser());
		if(categoryOp.isPresent()) throw new CustomException("Category already exist", HttpStatus.BAD_REQUEST);

		category = categoryRepo.save(category);

		return convertToDto(category);
	}

	@Transactional
	public CategoryResDto update(CategoryEditReqDto reqDto) {
		// Check category exist with this name
		Optional<Category> categoryOp = categoryRepo.findById(reqDto.getId());
		if(!categoryOp.isPresent()) throw new CustomException("Category not exist", HttpStatus.BAD_REQUEST);

		Category category = categoryOp.get();
		category.setName(reqDto.getName());
		category.setType(reqDto.getType());
		category = categoryRepo.save(category);

		return convertToDto(category);
	}

	@Transactional
	public void deleteById(Long id) {
		// Check category exist with this name
		Optional<Category> categoryOp = categoryRepo.findById(id);
		if(!categoryOp.isPresent()) throw new CustomException("Category not exist", HttpStatus.BAD_REQUEST);

		categoryRepo.delete(categoryOp.get());
	}

	public PageResponse<CategoryResDto> getAll(int page, int size, String sortBy, String sortDir) {
		// Create sort object
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// Create pageable object
		Pageable pageable = PageRequest.of(page, size, sort);

		// Get page from repository
		Page<Category> categoryPage = categoryRepo.findAllByUser(loggedinUser(), pageable);

		// Convert to DTO page
		List<CategoryResDto> content = categoryPage.getContent().stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());

		// Return custom page response
		return new PageResponse<>(
			content, 
			categoryPage.getNumber(), 
			categoryPage.getSize(),
			categoryPage.getTotalElements(), 
			categoryPage.getTotalPages(), 
			categoryPage.isFirst(),
			categoryPage.isLast()
		);
	}

	private CategoryResDto convertToDto(Category category) {
		// Your conversion logic here
		CategoryResDto dto = new CategoryResDto();
		dto.setId(category.getId());
		dto.setName(category.getName());
		dto.setType(category.getType());
		// set other fields
		return dto;
	}

	public CategoryResDto findById(@NonNull Long id) {
		Category category = categoryRepo.findById(id).orElseThrow(() -> new CustomException("Category not exist", HttpStatus.NOT_FOUND));
		return convertToDto(category);
	}

}
