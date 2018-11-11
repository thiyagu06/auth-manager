/**
 * 
 */
package com.izettle.authmanagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.izettle.authmanagement.common.UserCredentialType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The user credential entity.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_credential")
public class UserCredentialEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserCredentialEntity() {
		super();
	}

	/**
	 * The password field.
	 */
	@Column(name = "password")
	private String password;

	/**
	 * The user credential type field
	 */
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private UserCredentialType type;

	/**
	 * The user id field
	 */
	@Column(name = "userid")
	private String userid;

	/**
	 * The passwordexpireson field
	 */
	@Column(name = "password_expires_on")
	private LocalDateTime passwordExpiresOn;
}
