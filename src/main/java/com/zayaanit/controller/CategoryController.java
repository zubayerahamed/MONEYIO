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

import com.zayaanit.dto.CategoryCreateReqDto;
import com.zayaanit.dto.CategoryEditReqDto;
import com.zayaanit.dto.CategoryResDto;
import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.PageResponse;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.ReturnResponse;
import com.zayaanit.service.CategoryService;

import jakarta.validation.Valid;

/**
 * Zubayer Ahamed
 * 
 * @since May 8, 2026
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<ReturnResponse<PageResponse<CategoryResDto>>> getAll(
		@RequestParam(defaultValue = "0") int page, 
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy, 
		@RequestParam(defaultValue = "asc") String sortDir) 
	{
		PageResponse<CategoryResDto> resData = categoryService.getAll(page, size, sortBy, sortDir);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReturnResponse<CategoryResDto>> findById(@PathVariable Long id) {
		CategoryResDto resData = categoryService.findById(id);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	}

	@PostMapping
	public ResponseEntity<ReturnResponse<CategoryResDto>> create(@Valid @RequestBody CategoryCreateReqDto reqDto) {
		CategoryResDto resData = categoryService.create(reqDto);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, resData);
	}

	@PutMapping
	public ResponseEntity<ReturnResponse<CategoryResDto>> update(@Valid @RequestBody CategoryEditReqDto reqDto) {
		CategoryResDto resData = categoryService.update(reqDto);
		return ResponseBuilder.build(ResponseStatusType.UPDATE_SUCCESS, resData);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ReturnResponse<CategoryResDto>> delete(@PathVariable Long id) {
		categoryService.deleteById(id);
		return ResponseBuilder.build(ResponseStatusType.DELETE_SUCCESS, null);
	}
}
