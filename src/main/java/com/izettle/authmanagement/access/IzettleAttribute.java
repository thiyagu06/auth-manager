package com.izettle.authmanagement.access;

import org.springframework.security.access.ConfigAttribute;

public interface IzettleAttribute extends ConfigAttribute{

	public PermissionAttribute getPermission();
}
