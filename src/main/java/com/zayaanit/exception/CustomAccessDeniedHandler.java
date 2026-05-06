package com.zayaanit.exception;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void handle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull AccessDeniedException accessDeniedException) throws IOException, ServletException {

		ErrorResponse error = ErrorResponse.builder()
				.status(HttpStatus.FORBIDDEN.value()).error("Forbidden")
				.message("You do not have permission to access this resource")
				.build();

		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), error);
	}
}
