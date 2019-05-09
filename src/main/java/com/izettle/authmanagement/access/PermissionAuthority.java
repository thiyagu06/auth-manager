package com.izettle.authmanagement.access;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class PermissionAuthority implements GrantedAuthority {

    public String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
