package com.izettle.authmanagement.access;

import java.util.Collection;
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
        String roles = securityAttribute.get().getAttribute();
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
            Collectors.joining(","));
        if(roles.contains(authorities)) return ACCESS_GRANTED;

        return ACCESS_ABSTAIN;

    }
}