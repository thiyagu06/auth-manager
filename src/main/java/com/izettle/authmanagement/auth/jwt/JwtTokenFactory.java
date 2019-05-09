package com.izettle.authmanagement.auth.jwt;

import com.izettle.authmanagement.access.PermissionAuthority;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.izettle.authmanagement.dto.login.LoggedInUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * ##Changes
 * Updates to use the JWT setting instance
 * 
 * The class for Jwt token processing.
 * 
 * @author thiyagu
 * @version 1.1
 *
 */
@Component
public class JwtTokenFactory {
	
	/**
	 * Jwt settings instance
	 */
	@Autowired
	private JwtSettings jwtSettings;

	/**
	 * Parse the given JWT Token
	 * 
	 * @param token
	 * @return claims
	 */
	public Claims parseToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSettings.getSecret().getBytes()).parseClaimsJws(token.replace(jwtSettings.getHeaderPrefix(), "")).getBody();
		return claims;
	}

	/**
	 * Validate the given JWT token.
	 * 
	 * @param authToken
	 * @return boolean
	 */
	public boolean validateToken(String authToken) {
		try {
			String token = authToken.replace(jwtSettings.getHeaderPrefix(), "").trim();
			Jwts.parser().setSigningKey(jwtSettings.getSecret().getBytes()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Generate the token.
	 * 
	 * @param authentication
	 * @return string
	 */
	public String generateToken(Authentication authentication) {
		LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(jwtSettings.getExpirationTimeInSeconds());
		LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) authentication.getPrincipal();
		String token = Jwts.builder().setSubject(authentication.getName())
				.claim("roles","READ,WRITE")
				.claim("userId", loggedInUserDetails.getUserId()).signWith(SignatureAlgorithm.HS512, jwtSettings.getSecret().getBytes())
				.setExpiration(Date.from(expiresAt.atZone((ZoneId.systemDefault())).toInstant())).compact();
		return token;
	}
}
