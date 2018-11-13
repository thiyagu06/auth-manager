package com.izettle.authmanagement.dto.login;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * The logged in history dto.
 * 
 * @author Thiyagu
 * @version
 */
@Data
public class LoginAttempt {

	/**
	 * The loggedInAt field
	 */
	private LocalDateTime loggedInAt;

	/**
	 * For now we returning only success attempts. so not sending the success field
	 * in response. The success field
	 *
	 */
	@JsonIgnore
	private boolean success;

}
