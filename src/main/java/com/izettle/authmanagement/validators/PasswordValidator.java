package com.izettle.authmanagement.validators;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.izettle.authmanagement.dto.user.UserCredential;
import com.izettle.authmanagement.dto.user.UserRegistration;

/**
 * The validator class to validate the password.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Component("passwordValidator")
public class PasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistration.class.equals(clazz);
	}

	/**
	 * Method to validate both password 
	 * and confirm password fields matches or not.
	 * 
	 */
	@Override
	public void validate(Object target, Errors errors) {
		UserRegistration registration = (UserRegistration) target;
		UserCredential userCredential = registration.getUserCredential();
		if (userCredential != null && !StringUtils.isEmpty(userCredential.getPassword())
				&& !userCredential.getPassword().equals(userCredential.getConfirmPassword())) {
			errors.rejectValue("userCredential.password", "password not match", "password do not match");
			errors.rejectValue("userCredential.confirmPassword", "password not match", "password do not match");
		}
	}

}
