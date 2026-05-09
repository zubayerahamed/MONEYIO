package com.zayaanit.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zayaanit.entity.User;

/**
 * Zubayer Ahamed
 * Apr 22, 2026
 */
public class MyUserDetail implements UserDetails {

	private static final long serialVersionUID = 5225729158993121459L;

	private Long id;
	private String name;
	private String email;
	private String password;
	private String roles;
	private List<GrantedAuthority> authorities;
	private User user;

	public MyUserDetail(User user){
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.user = user;

		this.roles = "ROLE_USER";
		this.authorities = Arrays.stream(roles.split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	public User getUser() {
		return this.user;
	}

	public List<String> getRoles(){
		return Arrays.stream(roles.split(",")).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public @Nullable String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public Long getUserId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

}
