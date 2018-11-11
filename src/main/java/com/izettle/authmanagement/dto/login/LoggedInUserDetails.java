package com.izettle.authmanagement.dto.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The LoggedIn user details to store in the security context principal.
 * 
 * @author Thiyagu
 * @version
 *
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class LoggedInUserDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	public LoggedInUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			String userId) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
	}

	public LoggedInUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String userId) {
		super(username, password, authorities);
		this.userId = userId;
	}
}
