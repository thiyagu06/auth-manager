package com.izettle.authmanagement.access;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;
import org.springframework.util.ClassUtils;

public class SecurityMetadataSource extends AbstractMethodSecurityMetadataSource {
    @Override
    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {

        //consult https://github.com/spring-projects/spring-security/blob/master/core/src/main/java/org
        // /springframework/security/access/prepost/PrePostAnnotationSecurityMetadataSource.java
        //to implement findAnnotation
        PermissionRequired annotation = findAnnotation(method, targetClass, PermissionRequired.class);
        if (annotation != null) {
            return Collections.singletonList(new SecurityAttribute(Arrays.asList(annotation.value())));
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    private <A extends Annotation> A findAnnotation(
        Method method, Class<?> targetClass,
        Class<A> annotationClass
    ) {
        // The method may be on an interface, but we need attributes from the target
        // class.
        // If the target class is null, the method will be unchanged.
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        A annotation = AnnotationUtils.findAnnotation(specificMethod, annotationClass);

        if (annotation != null) {
            logger.debug(annotation + " found on specific method: " + specificMethod);
            return annotation;
        }

        // Check the original (e.g. interface) method
        if (specificMethod != method) {
            annotation = AnnotationUtils.findAnnotation(method, annotationClass);

            if (annotation != null) {
                logger.debug(annotation + " found on: " + method);
                return annotation;
            }
        }

        // Check the class-level (note declaringClass, not targetClass, which may not
        // actually implement the method)
        annotation = AnnotationUtils.findAnnotation(
            specificMethod.getDeclaringClass(),
            annotationClass
        );

        if (annotation != null) {
            logger.debug(annotation + " found on: "
                + specificMethod.getDeclaringClass().getName());
            return annotation;
        }

        return null;
    }

}