package com.izettle.authmanagement.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The jwt settings values
 * @author Thiyagu
 *
 */
@Configuration
@ConfigurationProperties(prefix = "jwt.auth")
@Data
public class JwtSettings {

	/**
	 * The secret field
	 */
	private String secret;
	
	/**
	 * The authHeaderkey field
	 */
	private String authHeaderKey;
	
	/**
	 * The authHederPrefix field
	 */
	private String headerPrefix;
	
	/**
	 * The authHederPrefix field
	 */
	private int expirationTimeInSeconds;
	
}
