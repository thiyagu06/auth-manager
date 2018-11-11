package com.izettle.authmanagement.dto.user;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The UserRegistration request representation.
 * 
 * @author Thiyagu
 *
 */
@Data
@EqualsAndHashCode
public class UserRegistration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The user details field.
	 */
	@Valid
	@NotNull(message = "user details must not be null")
	private User userDetails;

	/**
	 * The user credential field.
	 */
	@Valid
	@NotNull(message = "user credential must not be null")
	private UserCredential userCredential;
}
