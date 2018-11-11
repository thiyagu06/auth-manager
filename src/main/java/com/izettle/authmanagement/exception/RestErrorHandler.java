package com.izettle.authmanagement.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The controller advice for handling errors.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@RestControllerAdvice
public class RestErrorHandler {

	/**
	 * The method to handle the MethodArgumentNotValidException.
	 * 
	 * @param ex
	 * @return ValidationErrorDTO
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}

	/**
	 * The method to create all the field error as validation error response.
	 * 
	 * @param fieldErrors
	 * @return
	 */
	private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
		ValidationErrorDTO dto = new ValidationErrorDTO();

		for (FieldError fieldError : fieldErrors) {
			dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return dto;
	}
}
