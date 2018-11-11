package com.izettle.authmanagement.dto.user;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.izettle.authmanagement.common.AccountStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The User representation.
 * 
 * @author Thiyagu
 *
 */
@Data
@EqualsAndHashCode
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID field
	 */
	@JsonProperty(access = Access.READ_ONLY)
	private String id;

	/**
	 * The firstname field
	 * 
	 */
	@NotEmpty(message = "firstname should not be empty")
	@Size(max = 20, min = 3, message = "First name must be minimum {min} characters and maximum {max} characters")
	private String firstName;

	/**
	 * The lastname field
	 */
	@NotEmpty(message = "lastname should not be empty")
	@Size(max = 20, min = 3, message = "Last name must be minimum {min} characters and maximum {max} characters")
	private String lastName;

	/**
	 * The email field
	 */
	@NotEmpty(message = "email should not be empty")
	@Email(message = "invalid email format")
	private String email;

	/**
	 * The account status field
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	private AccountStatus status = AccountStatus.ACTIVE;

	/**
	 * The account expires on field
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	private LocalDateTime expiresOn = LocalDateTime.now().plusDays(60);

}
