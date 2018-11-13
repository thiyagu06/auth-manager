package com.izettle.authmanagement.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.izettle.authmanagement.dto.user.User;
import com.izettle.authmanagement.dto.user.UserRegistration;
import com.izettle.authmanagement.entity.UserEntity;
import com.izettle.authmanagement.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UniqueEmailValidatorTest {

	@Mock
	private UserRepository userRepositoryMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	@InjectMocks
	private UniqueEmailValidator uniqueEmailValidator;
	
	@Test
	public void testEmailAlreadyExists() {
		Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new UserEntity()));
		UserRegistration userRegistration = new UserRegistration();
		User user = new User();
		user.setEmail("thiyagu103@gmail.com");
		userRegistration.setUserDetails(user);
		Errors errors = new BeanPropertyBindingResult(userRegistration, "uniqueEmail");
		uniqueEmailValidator.validate(userRegistration, errors);
		assertTrue("email already exists true", errors.hasErrors());
		assertEquals(1,errors.getErrorCount());
		assertEquals("Given email is not unique", "email Id already exists", errors.getFieldError("userDetails.email").getDefaultMessage());
	}
	
	@Test
	public void testEmailAlreadyNotExists() {
		Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		UserRegistration userRegistration = new UserRegistration();
		User user = new User();
		user.setEmail("thiyagu103@gmail.com");
		userRegistration.setUserDetails(user);
		Errors errors = new BeanPropertyBindingResult(userRegistration, "passwordMisMatch");
		uniqueEmailValidator.validate(userRegistration, errors);
		assertFalse("email already not exists true", errors.hasErrors());
		assertEquals(0,errors.getErrorCount());
	}
}
