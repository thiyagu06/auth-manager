package com.izettle.authmanagement.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izettle.authmanagement.auth.jwt.JwtTokenUtil;
import com.izettle.authmanagement.dto.login.UserLoginRequest;

/**
 * The authentication filter to validate the user credentials
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
public class UsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public UsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			UserLoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequest.class);
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword());
			return this.authenticationManager.authenticate(authRequest);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String token = JwtTokenUtil.generateToken(auth);
		res.addHeader("Authorization", "Bearer " + token);
	}

}
