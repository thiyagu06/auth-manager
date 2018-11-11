package com.izettle.authmanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.izettle.authmanagement.dto.login.LoggedInHistory;

/**
 * The service for LoggedinHistory
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
public interface LoggedinHistoryService {

	/**
	 * creates loggedInhistory entry as successful.
	 * 
	 * @param loggedInHistory
	 */
	public void createSuccessLogin(LoggedInHistory loggedInHistory);

	/**
	 * return all the successful logged in history filtered by user id and page
	 * request.
	 * 
	 * @param userId
	 * @param pageRequest
	 * @return
	 */
	public List<LoggedInHistory> getLoggedInSuccessHistory(String userId, Pageable pageRequest);
}
