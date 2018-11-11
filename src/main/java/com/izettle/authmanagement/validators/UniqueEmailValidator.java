package com.izettle.authmanagement.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.izettle.authmanagement.dto.user.UserRegistration;
import com.izettle.authmanagement.repository.UserRepository;

/**
 * The unique email validator.
 * 
 * @author Thiyagu
 * @version 1.0
 */
@Component
public class UniqueEmailValidator implements Validator {

	/**
	 * The user repository field
	 */
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistration.class == clazz;
	}

	/**
	 * method validate whether email is already register or not.
	 * 
	 */
	@Override
	public void validate(Object target, Errors errors) {
		UserRegistration registration = (UserRegistration) target;
		if (userRepository.findByEmail(registration.getUserDetails().getEmail()).isPresent()) {
			errors.rejectValue("userDetails.email", "email Id already exists");
		}
	}

}
