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

import com.izettle.authmanagement.dto.login.LoggedInHistory;
import com.izettle.authmanagement.dto.login.LoggedInUserDetails;
import com.izettle.authmanagement.service.LoggedinHistoryService;

/**
 * The entry point for all the /loggedinHistory requests.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/loggedinHistory")
public class LoggedInHistoryController {

	/**
	 * The loggedinHistory service bean.
	 */
	@Autowired
	private LoggedinHistoryService loggedinHistoryService;

	/**
	 * Method to return all the top 5 recent successful login attempts.
	 * 
	 * @return list Of loggedInHistory
	 */
	@GetMapping("/success")
	public ResponseEntity<List<LoggedInHistory>> getSuccessfulLoggedInHistory() {
		Pageable request = PageRequest.of(0, 5, Direction.DESC, "loggedInAt");
		String loggedInUserId = ((LoggedInUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal()).getUserId();
		List<LoggedInHistory> loggedInHistories = loggedinHistoryService.getLoggedInSuccessHistory(loggedInUserId,
				request);
		return new ResponseEntity<List<LoggedInHistory>>(loggedInHistories, HttpStatus.OK);

	}
}
