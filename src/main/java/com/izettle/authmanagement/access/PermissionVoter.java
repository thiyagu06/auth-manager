package com.izettle.authmanagement.access;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class PermissionVoter implements AccessDecisionVoter<MethodInvocation> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof SecurityAttribute;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MethodInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public int vote(Authentication authentication, MethodInvocation object, Collection<ConfigAttribute> attributes) {
        Optional<SecurityAttribute> securityAttribute = attributes.stream()
            .filter(attr -> attr instanceof SecurityAttribute).map(SecurityAttribute.class::cast).findFirst();
        if (!securityAttribute.isPresent()) {
            return AccessDecisionVoter.ACCESS_ABSTAIN;
        }
        List<String> roles = Arrays.asList(securityAttribute.get().getAttribute().split("\\s*,\\s*"));
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
            Collectors.toList());
        boolean hasAccess = authorities.stream().anyMatch(element -> roles.contains(element));
        if(hasAccess) {
        	return AccessDecisionVoter.ACCESS_GRANTED;
        }
        return ACCESS_ABSTAIN;

    }
}