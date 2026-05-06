package com.zayaanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.zayaanit.dto.AuthenticationReqDto;
import com.zayaanit.dto.AuthenticationResDto;
import com.zayaanit.dto.RegisterRequestDto;
import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.ReturnResponse;
import com.zayaanit.service.AuthenticationService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * Zubayer Ahamed
 * Apr 22, 2026
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired private AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<ReturnResponse<AuthenticationResDto>> register(@Valid @RequestBody RegisterRequestDto request) {
		AuthenticationResDto data = authenticationService.register(request);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, data);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<ReturnResponse<AuthenticationResDto>> authenticate(@Valid @RequestBody AuthenticationReqDto request) {
		AuthenticationResDto data = authenticationService.authenticate(request);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, data);
	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException, java.io.IOException {
		authenticationService.refreshToken(request, response);
	}
}
