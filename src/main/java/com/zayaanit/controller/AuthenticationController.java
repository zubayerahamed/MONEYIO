package com.zayaanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zayaanit.dto.AuthenticationResDto;
import com.zayaanit.dto.RegisterRequestDto;
import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.ReturnResponse;
import com.zayaanit.service.AuthenticationService;

/**
 * Zubayer Ahamed
 * Apr 22, 2026
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired private AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<ReturnResponse<AuthenticationResDto>> register(@RequestBody RegisterRequestDto request) {
		AuthenticationResDto data = authenticationService.register(request);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, data);
	}

	
}
