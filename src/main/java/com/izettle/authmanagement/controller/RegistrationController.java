package com.izettle.authmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.izettle.authmanagement.dto.user.UserRegistration;
import com.izettle.authmanagement.service.RegistrationService;

/**
 * The entry point for the /register requests.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/register")
public class RegistrationController {

	/**
	 * The registration service bean
	 * 
	 */
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	@Qualifier("passwordValidator")
	private Validator passwordValidator;
	
	@Autowired
	@Qualifier("uniqueEmailValidator")
	private Validator uniqueEmailValidator;

	/**
	 * method to register the user.
	 * 
	 * @param userRegistration
	 * @return
	 */
	@PostMapping
	public ResponseEntity<UserRegistration> registerUser(@RequestBody @Valid UserRegistration userRegistration) {
		UserRegistration registedUser = registrationService.registerUser(userRegistration);
		return new ResponseEntity<UserRegistration>(registedUser, HttpStatus.CREATED);
	}
	
	@InitBinder("userRegistration")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(passwordValidator);
        binder.addValidators(uniqueEmailValidator);
    }
}
