package com.izettle.authmanagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The LoggedIn history entity.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "logged_in_history")
public class LoggedinHistoryEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The loggedInAt field
	 */
	@Column(name = "logged_in_at", nullable = false)
	private LocalDateTime loggedInAt;

	/**
	 * The success field
	 */
	@Column(name = "success", nullable = false)
	private boolean success;

	/**
	 * The userId field
	 */
	@Column(name = "userid", nullable = false)
	private String userId;

}
