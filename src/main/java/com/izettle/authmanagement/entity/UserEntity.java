/**
 * 
 */
package com.izettle.authmanagement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.izettle.authmanagement.common.AccountStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The user entity.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserEntity() {
		super();
	}

	/**
	 * The firstname field
	 */
	@Column(name = "firstname", nullable = false)
	private String firstname;

	/**
	 * The lastname field
	 */
	@Column(name = "lastname", nullable = false)
	private String lastname;

	/**
	 * The email field
	 */
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	/**
	 * The account status field
	 * 
	 */
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	/**
	 * The account expires on field
	 */
	@Column(name = "account_expires_on", nullable = false)
	private LocalDateTime accountExpiresOn;

}
