package com.izettle.authmanagement.auth.events;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.izettle.authmanagement.dto.login.LoggedInUserDetails;
import com.izettle.authmanagement.entity.LoginAttemptEntity;
import com.izettle.authmanagement.repository.LoginAttemptRepository;

/**
 * The Event Listener listen for authentication success event and save the data
 * into login_attempts table.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Component
public class AuthenticationEventListener {

	/**
	 * The loginAttempt repository field
	 */
	@Autowired
	private LoginAttemptRepository loginAttemptRepository;

	/**
	 * The listener method listen for authentication success event.
	 * 
	 * @param event
	 */
	@EventListener
	public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
		LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) event.getAuthentication().getPrincipal();
		LoginAttemptEntity loginAttemptEntity = new LoginAttemptEntity();
		loginAttemptEntity.setSuccess(true);
		loginAttemptEntity.setLoggedInAt(LocalDateTime.now());
		loginAttemptEntity.setUserId(loggedInUserDetails.getUserId());
		loginAttemptRepository.save(loginAttemptEntity);
	}

}
