package com.izettle.authmanagement.auth.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.izettle.authmanagement.common.AccountStatus;
import com.izettle.authmanagement.dto.login.LoggedInUserDetails;
import com.izettle.authmanagement.entity.UserCredentialEntity;
import com.izettle.authmanagement.entity.UserEntity;
import com.izettle.authmanagement.repository.UserCredentialRepository;
import com.izettle.authmanagement.repository.UserRepository;

/**
 * The UserDetails Service implementation used by DAO authentication provider.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static String READ_PERMISSION = "read";

	private static String WRITE_PERMISSION = "write";

	/**
	 * The user repository field
	 * 
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * The user credential repository field
	 */
	@Autowired
	private UserCredentialRepository userCredentialRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userEntityOptional = userRepository.findByEmail(username);
		userEntityOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found in the system"));
		UserEntity userEntity = userEntityOptional.get();
		if (AccountStatus.ACTIVE.equals(userEntity.getStatus())) {
			Optional<UserCredentialEntity> userCredentialOptional = userCredentialRepository
					.findByUserid(userEntity.getId());
			AccountStatus accountStatus = userEntity.getStatus();
			return new LoggedInUserDetails(username, userCredentialOptional.get().getPassword(),
					AccountStatus.ACTIVE.equals(accountStatus), !AccountStatus.EXPIRED.equals(accountStatus),
					isCredentialNonExpired(userCredentialOptional.get()), !AccountStatus.LOCKED.equals(accountStatus),
					new ArrayList<>(), userEntity.getId(),"US");
		}
		throw new DisabledException("User is not activated");
	}

	/**
	 * The method checks whether user credential is expired or not.
	 * 
	 * @param userCredentialEntity
	 * @return
	 */
	private boolean isCredentialNonExpired(UserCredentialEntity userCredentialEntity) {
		return userCredentialEntity.getPasswordExpiresOn().isAfter(LocalDateTime.now());
	}
}
