package com.izettle.authmanagement.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izettle.authmanagement.annotation.Validatable;
import com.izettle.authmanagement.validators.JSRValidator;

/**
 * The Aspect defines the method arguments should be annotated with
 * {@link Validatable} before execution of the method.
 * 
 * @author Thiyagu
 * @version 1.0
 * 
 */
@Aspect
@Component
public class ValidationAspect {

	/**
	 * The jsr validator bean
	 */
	@Autowired
	private JSRValidator jsrValidator;

	/**
	 * The method performs the validation before executing the all the service
	 * methods annotated with {@link Validatable}
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("@annotation(com.izettle.authmanagement.annotation.Validatable)")
	public void validateMethodParams(JoinPoint joinPoint) throws Throwable {
		jsrValidator.validateObject(joinPoint.getArgs());
	}
}
