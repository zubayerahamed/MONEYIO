package com.zayaanit.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zayaanit.model.MyUserDetail;
import com.zayaanit.repo.TokenRepo;
import com.zayaanit.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Zubayer Ahamed
 * @since Jun 22, 2025
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserService usersService;
	private final TokenRepo xtokensRepo;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request, 
		@NonNull HttpServletResponse response, 
		@NonNull FilterChain filterChain) throws ServletException, IOException {

		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String jwtToken;
		final String username;   // email address

		if(StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwtToken = authHeader.substring(7);
		username = jwtService.extractUsername(jwtToken);

		//Integer workspaceId = jwtService.extractClaim(jwtToken, claims -> claims.get("workspaceId", Integer.class));
		if(StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {  // User id not null && user is not authenticated yet
			MyUserDetail myUserDetails = (MyUserDetail) usersService.loadUserByUsername(username);

			var isTokenValid = xtokensRepo.findByToken(jwtToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);

			if(jwtService.isTokenValid(jwtToken, myUserDetails) && isTokenValid) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

}
