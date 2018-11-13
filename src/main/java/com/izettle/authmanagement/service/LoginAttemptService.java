package com.izettle.authmanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.izettle.authmanagement.dto.login.LoginAttempt;

/**
 * The service for loginAttempt
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
public interface LoginAttemptService {

	/**
	 * creates loginAttempt entry as successful.
	 * 
	 * @param loginAttempt
	 */
	public void createSuccessLoginAttempt(LoginAttempt loginAttempt);

	/**
	 * return all the successful loginAttempt filtered by user id and page
	 * request.
	 * 
	 * @param userId
	 * @param pageRequest
	 * @return
	 */
	public List<LoginAttempt> getSuccessfulLoginAttempts(String userId, Pageable pageRequest);
}
