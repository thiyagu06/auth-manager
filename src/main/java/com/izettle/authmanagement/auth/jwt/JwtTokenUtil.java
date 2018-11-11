package com.izettle.authmanagement.auth.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.security.core.Authentication;

import com.izettle.authmanagement.dto.login.LoggedInUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The util class for Jwt token processing.
 * 
 * @author thiyagu
 * @version 1.0
 *
 */
public class JwtTokenUtil {

	private JwtTokenUtil() {
		// private constructor
	}

	/**
	 * Parse the given JWT Token
	 * 
	 * @param token
	 * @return claims
	 */
	public static Claims parseToken(String token) {
		Claims claims = Jwts.parser().setSigningKey("SECRET").parseClaimsJws(token.replace("Bearer ", "")).getBody();
		return claims;
	}

	/**
	 * Validate the given JWT token.
	 * 
	 * @param authToken
	 * @return boolean
	 */
	public static boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey("SECRET").parseClaimsJws(authToken.replace("Bearer ", ""));
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
	public static String generateToken(Authentication authentication) {
		LocalDateTime expiresAt = LocalDateTime.now().plusHours(8);
		LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) authentication.getPrincipal();
		String token = Jwts.builder().setSubject(authentication.getName())
				.claim("userId", loggedInUserDetails.getUserId()).signWith(SignatureAlgorithm.HS512, "SECRET")
				.setExpiration(Date.from(expiresAt.atZone((ZoneId.systemDefault())).toInstant())).compact();
		return token;
	}
}
