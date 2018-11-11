package com.izettle.authmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.izettle.authmanagement.common.AccountStatus;
import com.izettle.authmanagement.dto.user.User;
import com.izettle.authmanagement.dto.user.UserCredential;
import com.izettle.authmanagement.dto.user.UserRegistration;
import com.izettle.authmanagement.entity.UserCredentialEntity;
import com.izettle.authmanagement.entity.UserEntity;
import com.izettle.authmanagement.repository.UserCredentialRepository;
import com.izettle.authmanagement.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserRegsitrationServiceTest {

	@Mock
	private UserRepository userRepositoryMock;

	@Mock
	private UserCredentialRepository usercrdentialRepositoryMock;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoderMock;

	@InjectMocks
	private RegistrationServiceImpl userRegistrationService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void regsiterUserSuccess() {
		UserEntity entity = Mockito.mock(UserEntity.class);
		Mockito.when(userRepositoryMock.save(Mockito.any(UserEntity.class))).thenReturn(entity);
		UserCredentialEntity userCredentialEntity = Mockito.mock(UserCredentialEntity.class);
		Mockito.when(usercrdentialRepositoryMock.save(Mockito.any(UserCredentialEntity.class)))
				.thenReturn(userCredentialEntity);
		UserRegistration userRegistrationMock = new UserRegistration();
		User mockUser = Mockito.mock(User.class);
		UserCredential mockCredentials = Mockito.mock(UserCredential.class);
		mockCredentials.setPassword("121323");
		userRegistrationMock.setUserDetails(mockUser);
		userRegistrationMock.setUserCredential(mockCredentials);
		userRegistrationMock.getUserDetails().setStatus(AccountStatus.ACTIVE);
		userRegistrationService.registerUser(userRegistrationMock);
		ArgumentCaptor<UserEntity> userEntityArgument = ArgumentCaptor.forClass(UserEntity.class);
		Mockito.verify(userRepositoryMock, Mockito.times(1)).save(userEntityArgument.capture());
		Mockito.verifyNoMoreInteractions(userRepositoryMock);
		ArgumentCaptor<UserCredentialEntity> userCredentialEntityArgument = ArgumentCaptor
				.forClass(UserCredentialEntity.class);
		Mockito.verify(usercrdentialRepositoryMock, Mockito.times(1)).save(userCredentialEntityArgument.capture());
		Mockito.verifyNoMoreInteractions(usercrdentialRepositoryMock);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void regsiterUserSuccessFailure() {
		Mockito.when(userRepositoryMock.save(Mockito.any(UserEntity.class))).thenThrow(DataIntegrityViolationException.class);
		UserRegistration userRegistrationMock = new UserRegistration();
		User mockUser = Mockito.mock(User.class);
		UserCredential mockCredentials = Mockito.mock(UserCredential.class);
		userRegistrationMock.setUserDetails(mockUser);
		userRegistrationMock.setUserCredential(mockCredentials);
		userRegistrationMock.getUserDetails().setStatus(AccountStatus.ACTIVE);
		userRegistrationService.registerUser(userRegistrationMock);
		ArgumentCaptor<UserEntity> userEntityArgument = ArgumentCaptor.forClass(UserEntity.class);
		Mockito.verify(userRepositoryMock, Mockito.times(1)).save(userEntityArgument.capture());
		Mockito.verifyNoMoreInteractions(userRepositoryMock);
		ArgumentCaptor<UserCredentialEntity> userCredentialEntityArgument = ArgumentCaptor
				.forClass(UserCredentialEntity.class);
		Mockito.verify(usercrdentialRepositoryMock, Mockito.never()).save(userCredentialEntityArgument.capture());
	}
}
