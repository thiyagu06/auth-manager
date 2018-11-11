package com.izettle.authmanagement.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation used to annotate method as validatable.Used at the method
 * level and indicates that method arguments should be validate before
 * execution.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Validatable {

}
