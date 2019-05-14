package com.izettle.authmanagement.access;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PermissionAttribute {

	private String country;
	
	private List<Permission> access;
}
