package com.izettle.authmanagement.dto.login;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * The user login request representation.
 * 
 * @author Thiyagu
 *
 */
@Getter
public class UserLoginRequest {

	/**
	 * The user name field
	 * 
	 */
	private String username;

	/**
	 * The password field
	 */
	private String password;

	/**
	 * @param username
	 * @param password
	 */
	@JsonCreator
	public UserLoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
	}
}
