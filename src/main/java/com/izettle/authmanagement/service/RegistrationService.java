package com.izettle.authmanagement.service;

import com.izettle.authmanagement.dto.user.UserRegistration;

/**
 * The interface defines the contracts for user registration service.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
public interface RegistrationService {

	/**
	 * creates user registration.
	 * 
	 * @param userRegistration
	 * @return
	 */
	public UserRegistration registerUser(UserRegistration userRegistration);
}
