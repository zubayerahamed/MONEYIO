package com.zayaanit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.zayaanit.entity.User;
import com.zayaanit.exception.CustomException;
import com.zayaanit.model.MyUserDetail;
import com.zayaanit.repo.UserRepo;

/**
 * Zubayer Ahamed
 * May 8, 2026
 */
public abstract class BaseService {

	@Autowired private UserRepo userRepo;

	protected User loggedinUser() throws CustomException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			throw new CustomException("User not authenticated", HttpStatus.FORBIDDEN);
		}

		Object principal = auth.getPrincipal();
		if (principal instanceof MyUserDetail) {
			MyUserDetail mud = (MyUserDetail) principal;

			Optional<User> userOp = userRepo.findByEmail(mud.getUsername());
			if(!userOp.isPresent()) throw new CustomException("User not authenticated", HttpStatus.FORBIDDEN);
		
			return userOp.get();
		}

		throw new CustomException("User not authenticated", HttpStatus.FORBIDDEN);
	}
}
