package com.izettle.authmanagement.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * The base entity class for all the entity class.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Id field
	 */
	@Id
	private String id;

	public BaseEntity() {
		this.id = UUID.randomUUID().toString();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		return getId().equals(other.getId());
	}
}
