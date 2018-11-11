package com.izettle.authmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izettle.authmanagement.annotation.Validatable;
import com.izettle.authmanagement.common.UserCredentialType;
import com.izettle.authmanagement.dto.user.User;
import com.izettle.authmanagement.dto.user.UserCredential;
import com.izettle.authmanagement.dto.user.UserRegistration;
import com.izettle.authmanagement.entity.UserCredentialEntity;
import com.izettle.authmanagement.entity.UserEntity;
import com.izettle.authmanagement.repository.UserCredentialRepository;
import com.izettle.authmanagement.repository.UserRepository;

/**
 * The implementation of user registration service.
 * 
 * @author Thiyagu
 * @version
 *
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

	/**
	 * The user repository field
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * The user credential repository field
	 */
	@Autowired
	private UserCredentialRepository usercrdentialRepository;

	/**
	 * The bCryptPasswordEncoder field
	 */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * * The method creates entry in both user and user_credential tables.
	 */
	@Override
	@Transactional
	@Validatable
	public UserRegistration registerUser(UserRegistration userRegistration) {
		UserEntity savedUser = userRepository.save(toUserEntity(userRegistration));
		userRegistration.getUserDetails().setId(savedUser.getId());
		if (savedUser != null) {
			UserCredentialEntity userCredentialEntity = toUserCredentialEntity(userRegistration);
			userCredentialEntity.setUserid(savedUser.getId());
			usercrdentialRepository.save(userCredentialEntity);
		}

		return userRegistration;

	}

	/**
	 * The method to convert userRegistration into UserEntity object.
	 * 
	 * @param userRegistration
	 * @return {@link UserEntity}
	 */
	private UserEntity toUserEntity(UserRegistration userRegistration) {
		User request = userRegistration.getUserDetails();
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(request.getEmail());
		userEntity.setFirstname(request.getFirstName());
		userEntity.setLastname(request.getLastName());
		userEntity.setAccountExpiresOn(request.getExpiresOn());
		userEntity.setStatus(request.getStatus());
		return userEntity;
	}

	/**
	 * The method to convert userRegistration into UserCredentialEntity object.
	 * 
	 * @param userRegistration
	 * @return {@link UserCredentialEntity}
	 */
	private UserCredentialEntity toUserCredentialEntity(UserRegistration userRegistration) {
		UserCredential request = userRegistration.getUserCredential();
		UserCredentialEntity userCredentialEntity = new UserCredentialEntity();
		userCredentialEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		userCredentialEntity.setType(UserCredentialType.HASHED);
		userCredentialEntity.setPasswordExpiresOn(request.getExpiresOn());
		return userCredentialEntity;
	}

}
