package com.zayaanit.service;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.zayaanit.entity.Token;
import com.zayaanit.repo.TokenRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Zubayer Ahamed
 * Apr 22, 2026
 */
@Service
public class LogoutService implements LogoutHandler {

	@Autowired private TokenRepo tokensRepo;

	@Override
	public void logout(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Authentication authentication) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String jwtToken;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}

		jwtToken = authHeader.substring(7);

		Token storedToken = tokensRepo.findByToken(jwtToken).orElse(null);
		if(storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokensRepo.save(storedToken);
		}

	}
}
