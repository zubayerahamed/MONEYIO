package com.zayaanit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	@Transactional
	public AuthenticationResDto register(RegisterRequestDto request) {
		// 1. Check user exist already
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new CustomException("Email is already registered.", HttpStatus.BAD_REQUEST);
		}

		// 2. Create user
		User user = User.builder()
				.name(extractName(request.getEmail()))
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();

		user = userRepo.save(user);

		// 3. Generate JWT token and Refresh token
		var jwtToken = jwtService.generateToken(new MyUserDetail(user));
		var refreshToken = jwtService.generateRefreshToken(new MyUserDetail(user));

		// 4. Save User Token
		saveUserToken(user.getId(), jwtToken);

		return AuthenticationResDto.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	@Transactional
	private void saveUserToken(Long zuser, String jwtToken) {
		Token xtoken = Token.builder()
				.userId(zuser)
				.token(jwtToken)
				.revoked(false)
				.expired(false)
				.xtype(TokenType.BEARER)
				.build();

		tokenRepo.save(xtoken);
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
