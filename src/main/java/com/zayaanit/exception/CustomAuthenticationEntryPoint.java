package com.zayaanit.exception;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.zayaanit.model.ErrorResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

/**
 * Zubayer Ahamed
 * 
 * @since Jun 25, 2025
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull AuthenticationException authException) throws IOException, ServletException {

		ErrorResponse error = ErrorResponse.builder()
				.status(HttpStatus.UNAUTHORIZED.value())
				.error("Unauthorized")
				.message("Authentication is required to access this resource")
				.build();

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), error);
	}
}
