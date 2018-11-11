package com.izettle.authmanagement.validators;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

/**
 * The JSR validator class.
 * 
 * @author Thiyagu
 * @version 1.0
 */
@Component
public class JSRValidator {

	/**
	 * The application context.
	 */
	@Autowired
	ApplicationContext context;

	/**
	 * The method to validate given object.
	 * 
	 * @param objectToValidate
	 * @return boolean
	 */
	public boolean validateObject(Object objectToValidate) {
		Map<String, Validator> validatorMap = context.getBeansOfType(Validator.class);
		if (validatorMap == null) {
			return true;
		}

		DataBinder binder = new DataBinder(objectToValidate);
		for (Validator validator : validatorMap.values()) {

			if (validator.supports(objectToValidate.getClass())) {

				binder.addValidators(validator);
			}
		}
		binder.validate();
		BindingResult bindingResult = binder.getBindingResult();
		return !bindingResult.hasErrors();
	}
}
