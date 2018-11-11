package com.izettle.authmanagement.auth.events;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.izettle.authmanagement.dto.login.LoggedInUserDetails;
import com.izettle.authmanagement.entity.LoggedinHistoryEntity;
import com.izettle.authmanagement.repository.LoginHistoryRepository;

/**
 * The Event Listener listen for authentication success event and save the data
 * into logged_in_history table.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Component
public class AuthenticationEventListener {

	/**
	 * The loginHistory repository field
	 */
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;

	/**
	 * The listener method listen for authentication success event.
	 * 
	 * @param event
	 */
	@EventListener
	public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
		LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) event.getAuthentication().getPrincipal();
		LoggedinHistoryEntity loggedinHistoryEntity = new LoggedinHistoryEntity();
		loggedinHistoryEntity.setSuccess(true);
		loggedinHistoryEntity.setLoggedInAt(LocalDateTime.now());
		loggedinHistoryEntity.setUserId(loggedInUserDetails.getUserId());
		loginHistoryRepository.save(loggedinHistoryEntity);
	}

}
