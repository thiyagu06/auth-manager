package com.izettle.authmanagement.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.izettle.authmanagement.common.AccountStatus;
import com.izettle.authmanagement.common.UserCredentialType;
import com.izettle.authmanagement.entity.UserCredentialEntity;
import com.izettle.authmanagement.entity.UserEntity;
import com.izettle.authmanagement.repository.UserCredentialRepository;
import com.izettle.authmanagement.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserCredentialRepository userCredentialRespository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSuccessUserFound() {
		UserEntity userEntity = createUserEntity();
		Mockito.when(userRepository.findByEmail("thiyagu103@gmail.com")).thenReturn(Optional.of(userEntity));
		Mockito.when(userCredentialRespository.findByUserid(userEntity.getId()))
				.thenReturn(Optional.of(createUserCredentialEntity()));
		UserDetails loggedInUserDetails = userDetailsService.loadUserByUsername("thiyagu103@gmail.com");
		Assert.assertNotNull(loggedInUserDetails);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testFailureUserNotFound() {
		Mockito.when(userRepository.findByEmail("thiyagu103@gmail.com")).thenReturn(Optional.empty());
		userDetailsService.loadUserByUsername("thiyagu103@gmail.com");
	}

	private UserEntity createUserEntity() {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstname("Thiyagu");
		userEntity.setLastname("GK");
		userEntity.setStatus(AccountStatus.ACTIVE);
		userEntity.setEmail("thiyagu103@gmail.com");
		return userEntity;
	}

	private UserCredentialEntity createUserCredentialEntity() {
		UserCredentialEntity userCredentialEntity = new UserCredentialEntity();
		userCredentialEntity.setPassword("123455");
		userCredentialEntity.setType(UserCredentialType.HASHED);
		userCredentialEntity.setUserid("1233434");
		userCredentialEntity.setPasswordExpiresOn(LocalDateTime.now().plusDays(2));
		return userCredentialEntity;
	}
}
