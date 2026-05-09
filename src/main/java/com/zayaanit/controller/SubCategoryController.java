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

import com.zayaanit.dto.SubCategoryCreateReqDto;
import com.zayaanit.dto.SubCategoryEditReqDto;
import com.zayaanit.dto.SubCategoryResDto;
import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.PageResponse;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.ReturnResponse;
import com.zayaanit.service.SubCategoryService;

import jakarta.validation.Valid;

/**
 * Zubayer Ahamed
 * 
 * @since May 8, 2026
 */
@RestController
@RequestMapping("/api/v1/sub-categories")
public class SubCategoryController {

	@Autowired
	private SubCategoryService subCategoryService;

	@GetMapping
	public ResponseEntity<ReturnResponse<PageResponse<SubCategoryResDto>>> getAll(
		@RequestParam(required = true) Long categoryId, 
		@RequestParam(defaultValue = "0") int page, 
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy, 
		@RequestParam(defaultValue = "asc") String sortDir) 
	{
		PageResponse<SubCategoryResDto> resData = subCategoryService.getAll(categoryId, page, size, sortBy, sortDir);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReturnResponse<SubCategoryResDto>> findById(@PathVariable Long id) {
		SubCategoryResDto resData = subCategoryService.findById(id);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	}

	@PostMapping
	public ResponseEntity<ReturnResponse<SubCategoryResDto>> create(@Valid @RequestBody SubCategoryCreateReqDto reqDto) {
		SubCategoryResDto resData = subCategoryService.create(reqDto);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, resData);
	}

	@PutMapping
	public ResponseEntity<ReturnResponse<SubCategoryResDto>> update(@Valid @RequestBody SubCategoryEditReqDto reqDto) {
		SubCategoryResDto resData = subCategoryService.update(reqDto);
		return ResponseBuilder.build(ResponseStatusType.UPDATE_SUCCESS, resData);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ReturnResponse<SubCategoryResDto>> delete(@PathVariable Long id) {
		subCategoryService.deleteById(id);
		return ResponseBuilder.build(ResponseStatusType.DELETE_SUCCESS, null);
	}
}
