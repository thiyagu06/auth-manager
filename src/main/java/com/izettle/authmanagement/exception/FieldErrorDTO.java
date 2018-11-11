package com.izettle.authmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The error representation of fields.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@AllArgsConstructor
@Data
public class FieldErrorDTO {

	/**
	 * The field
	 */
	private final String field;

	/**
	 * The message
	 */
	private final String message;

}
