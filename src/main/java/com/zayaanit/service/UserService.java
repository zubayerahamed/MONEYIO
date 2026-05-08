package com.zayaanit.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zayaanit.entity.User;
import com.zayaanit.model.MyUserDetail;
import com.zayaanit.repo.UserRepo;

/**
 * Zubayer Ahamed
 * Apr 22, 2026
 */
@Service
public class UserService extends BaseService implements UserDetailsService  {

	@Autowired private UserRepo userRepo;

	@Override
	public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
		if(StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("Email required");
		}

		Optional<User> userOp = userRepo.findByEmail(username);
		if(!userOp.isPresent()) throw new UsernameNotFoundException("User not exist.");

		User user = userOp.get();

		return new MyUserDetail(user);
	}

}
