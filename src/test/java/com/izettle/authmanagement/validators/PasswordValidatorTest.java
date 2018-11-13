package com.izettle.authmanagement.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.izettle.authmanagement.dto.user.UserCredential;
import com.izettle.authmanagement.dto.user.UserRegistration;

public class PasswordValidatorTest {

	@Test
	public void testPasswordValidatorWithMismatchingPassword() {
		UserRegistration userRegistration = new UserRegistration();
		UserCredential userCredential = new UserCredential();
		userCredential.setPassword("123455");
		userCredential.setConfirmPassword("12");
		userRegistration.setUserCredential(userCredential);
		PasswordValidator validator = new PasswordValidator();
		Errors errors = new BeanPropertyBindingResult(userRegistration, "passwordMisMatch");
		validator.validate(userRegistration, errors);
		assertTrue("Passwords are mismatching", errors.hasErrors());
		assertEquals(2, errors.getErrorCount());
		assertEquals("password do not match", errors.getFieldError("userCredential.password").getDefaultMessage());
		assertEquals("password do not match", errors.getFieldError("userCredential.confirmPassword").getDefaultMessage());
	}

	@Test
	public void testPasswordValidatorWithmatchingPassword() {
		UserRegistration userRegistration = new UserRegistration();
		UserCredential userCredential = new UserCredential();
		userCredential.setPassword("123456");
		userCredential.setConfirmPassword("123456");
		userRegistration.setUserCredential(userCredential);
		PasswordValidator validator = new PasswordValidator();
		Errors errors = new BeanPropertyBindingResult(userRegistration, "passwordMisMatch");
		validator.validate(userRegistration, errors);
		assertFalse("Passwords are matching", errors.hasErrors());
		assertEquals(0, errors.getErrorCount());
	}
}
