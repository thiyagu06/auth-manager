package com.izettle.authmanagement.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * The class represents the validation error response format.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Getter
public class ValidationErrorDTO {

	private List<FieldErrorDTO> errors = new ArrayList<>();

	public ValidationErrorDTO() {

	}

	public void addFieldError(String path, String message) {
		FieldErrorDTO error = new FieldErrorDTO(path, message);
		errors.add(error);
	}
}
