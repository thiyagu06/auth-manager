package com.izettle.authmanagement.access;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.ConfigAttribute;

public class SecurityAttribute implements IzettleAttribute {
    private final List<Permission> permissions;
    
    private final PermissionAttribute permissionAttribute;

    public SecurityAttribute(
        List<Permission>
            permissions,PermissionAttribute permissionAttribute
    ) {
        this.permissions = permissions;
        this.permissionAttribute= permissionAttribute;
    }

    @Override
    public String getAttribute() {
        return permissions.stream().map(p -> p.name()).collect(Collectors.joining(","));
    }

	@Override
	public PermissionAttribute getPermission() {
		return this.permissionAttribute;
	}
}