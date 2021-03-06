package com.izettle.authmanagement.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.izettle.authmanagement.access.PermissionAuthority;
import com.izettle.authmanagement.auth.jwt.JwtSettings;
import com.izettle.authmanagement.auth.jwt.JwtTokenFactory;
import com.izettle.authmanagement.dto.login.LoggedInUserDetails;

import io.jsonwebtoken.Claims;

/**
 * The Authentication filter to validate the jwt token.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtSettings jwtSettings;

	private JwtTokenFactory jwtTokenFactory;

	public JWTAuthorizationFilter(AuthenticationManager authManager, JwtSettings jwtSettings,
			JwtTokenFactory jwtTokenFactory) {
		super(authManager);
		this.jwtSettings = jwtSettings;
		this.jwtTokenFactory = jwtTokenFactory;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer")) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		if (authentication == null) {
			throw new InsufficientAuthenticationException("Invalid Token Passed");
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(jwtSettings.getAuthHeaderKey());
		if (token != null && jwtTokenFactory.validateToken(token)) {
			// parse the token.
			Claims claims = jwtTokenFactory.parseToken(token);
			if (claims.getSubject() != null) {
				LoggedInUserDetails principal = new LoggedInUserDetails(claims.getSubject(), "", new ArrayList<>(),
					claims.get("userId", String.class),claims.get("country",String.class)
				);
				final Collection authorities = Arrays.stream(claims.get("roles").toString().split(",")).map(
					PermissionAuthority::new).collect(Collectors.toList());
				return new UsernamePasswordAuthenticationToken(principal, null, authorities);
			}
			return null;
		}
		return null;
	}

}
