/**
 * 
 */
package com.izettle.authmanagement.dto.user;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The User credential representation.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Data
@EqualsAndHashCode
public class UserCredential implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The password field. This field will not be sent in response.
	 */
	@Size(max = 12, min = 6, message = "password must be minimum {min} characters and maximum {max} characters")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	/**
	 * The confirmation password field. This field will not be sent in response.
	 */
	@Size(max = 12, min = 6, message = "password must be minimum {min} characters and maximum {max} characters")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String confirmPassword;

	/**
	 * The password expires on field. The field will should not be read from request.
	 */
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime expiresOn = LocalDateTime.now().plusDays(30);
}
