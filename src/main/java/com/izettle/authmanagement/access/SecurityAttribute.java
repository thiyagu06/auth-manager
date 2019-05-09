package com.izettle.authmanagement.access;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.ConfigAttribute;

public class SecurityAttribute implements ConfigAttribute {
    private final List<Permission> permissions;

    public SecurityAttribute(
        List<Permission>
            permissions
    ) {
        this.permissions = permissions;
    }

    @Override
    public String getAttribute() {
        return permissions.stream().map(p -> p.name()).collect(Collectors.joining(","));
    }
}