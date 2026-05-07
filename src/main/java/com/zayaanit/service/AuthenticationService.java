package com.zayaanit.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zayaanit.dto.AuthenticationReqDto;
import com.zayaanit.dto.AuthenticationResDto;
import com.zayaanit.dto.RegisterRequestDto;
import com.zayaanit.entity.Token;
import com.zayaanit.entity.User;
import com.zayaanit.enums.TokenType;
import com.zayaanit.exception.CustomException;
import com.zayaanit.model.MyUserDetail;
import com.zayaanit.repo.TokenRepo;
import com.zayaanit.repo.UserRepo;
import com.zayaanit.security.JwtService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

/**
 * Zubayer Ahamed Apr 22, 2026
 */
@Service
public class AuthenticationService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private TokenRepo tokenRepo;
	@Autowired
	private UserService userService;

	@Transactional
	public AuthenticationResDto register(RegisterRequestDto request) {
		// 1. Check user exist already
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new CustomException("Email is already registered.", HttpStatus.BAD_REQUEST);
		}

		// 2. Create user
		User user = new User();
		user.setName(extractName(request.getEmail()));
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user = userRepo.save(user);

		// 3. Generate JWT token and Refresh token
		var jwtToken = jwtService.generateToken(new MyUserDetail(user));
		var refreshToken = jwtService.generateRefreshToken(new MyUserDetail(user));

		// 4. Save User Token
		saveUserToken(user.getId(), jwtToken);

		return AuthenticationResDto.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	@Transactional
	public AuthenticationResDto authenticate(AuthenticationReqDto request) {
		// 1. Find user by email
		User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Email is not registered."));

		// 2. Verify password
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials.");
		}

		// 3. Generate JWT token and Refresh token
		var jwtToken = jwtService.generateToken(new MyUserDetail(user));
		var refreshToken = jwtService.generateRefreshToken(new MyUserDetail(user));

		// 4. Revoke previous token and save new token
		revokeAllUserTokens(user.getId());
		saveUserToken(user.getId(), jwtToken);

		return AuthenticationResDto.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	@Transactional
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, StreamWriteException, DatabindException, java.io.IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userId;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userId = jwtService.extractUsername(refreshToken);
		if (StringUtils.isNotBlank(userId)) {
			MyUserDetail userDetails = (MyUserDetail) userService.loadUserByUsername(userId);

			if (jwtService.isTokenValid(refreshToken, userDetails)) {
				var accessToken = jwtService.generateToken(userDetails);
				revokeAllUserTokens(Long.valueOf(userDetails.getUsername()));
				saveUserToken(Long.valueOf(userDetails.getUsername()), accessToken);
				var authResponse = AuthenticationResDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	@Transactional
	private void saveUserToken(Long zuser, String jwtToken) {
		if(zuser == null) return;

		Token xtoken = Token.builder()
				.user(userRepo.findById(zuser).get())
				.token(jwtToken)
				.revoked(false)
				.expired(false)
				.xtype(TokenType.BEARER)
				.build();

		tokenRepo.save(xtoken);
	}

	@Transactional
	private void revokeAllUserTokens(Long zuser) {
		List<Token> validTokens = tokenRepo.findAllByUserIdAndRevokedAndExpired(zuser, false, false);
		if (validTokens.isEmpty())
			return;

		validTokens.forEach(t -> {
			t.setRevoked(true);
			t.setExpired(true);
		});

		tokenRepo.saveAll(validTokens);
	}

	private String extractName(String email) {
		if (email == null || !email.contains("@")) {
			return "";
		}

		String localPart = email.substring(0, email.indexOf("@"));

		// Replace common separators with space
		String name = localPart.replaceAll("[._-]+", " ");

		// Remove numbers
		name = name.replaceAll("\\d+", "");

		// Capitalize each word
		String[] parts = name.trim().split("\\s+");
		StringBuilder formattedName = new StringBuilder();

		for (String part : parts) {
			if (!part.isEmpty()) {
				formattedName.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1).toLowerCase()).append(" ");
			}
		}

		return formattedName.toString().trim();
	}
}
