package com.izettle.authmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.izettle.authmanagement.access.Permission;
import com.izettle.authmanagement.access.PermissionRequired;
import com.izettle.authmanagement.dto.login.LoggedInUserDetails;
import com.izettle.authmanagement.dto.login.LoginAttempt;
import com.izettle.authmanagement.service.LoginAttemptService;

/**
 * The entry point for all the /loginAttempts requests.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/loginAttempts")
public class LoginAttemptsController {

	/**
	 * The loginAttempt service bean.
	 */
	@Autowired
	private LoginAttemptService loginAttemptService;

	/**
	 * Method to return all the top 5 recent successful login attempts.
	 * 
	 * @return list Of loginAttempt
	 */
	@PermissionRequired(value=Permission.READ,country="US")
	@GetMapping("/success")
	public ResponseEntity<List<LoginAttempt>> getSuccessfulLoggedInHistory() {
		Pageable request = PageRequest.of(0, 5, Direction.DESC, "loggedInAt");
		String loggedInUserId = ((LoggedInUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal()).getUserId();
		List<LoginAttempt> loginAttempts = loginAttemptService.getSuccessfulLoginAttempts(loggedInUserId,
				request);
		return new ResponseEntity<List<LoginAttempt>>(loginAttempts, HttpStatus.OK);

	}
}
