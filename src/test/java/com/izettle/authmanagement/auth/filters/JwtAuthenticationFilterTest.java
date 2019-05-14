package com.izettle.authmanagement.auth.filters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.izettle.authmanagement.auth.jwt.JwtSettings;
import com.izettle.authmanagement.auth.jwt.JwtTokenFactory;
import com.izettle.authmanagement.dto.login.LoggedInUserDetails;
import com.izettle.authmanagement.filters.JWTAuthorizationFilter;

import io.jsonwebtoken.Claims;

public class JwtAuthenticationFilterTest {

	private AuthenticationManager manager;

	private JWTAuthorizationFilter filter;

	private Authentication authentication;
	
	private JwtTokenFactory jwtTokenFactory;
	
	private JwtSettings jwtSettings;
	
	private Claims claims;

	@Before
	public void setUp() throws Exception {
		LoggedInUserDetails principal = new LoggedInUserDetails("USERNAME", "", new ArrayList<>(), "123445454545","US");
		UsernamePasswordAuthenticationToken rodRequest = new UsernamePasswordAuthenticationToken(principal, "testUser");
		rodRequest.setDetails(new WebAuthenticationDetails(new MockHttpServletRequest()));
		authentication = new UsernamePasswordAuthenticationToken(principal, "koala", new ArrayList<>());
		jwtTokenFactory = mock(JwtTokenFactory.class);
		manager = mock(AuthenticationManager.class);
		when(manager.authenticate(rodRequest)).thenReturn(authentication);
		when(jwtTokenFactory.generateToken(Mockito.any(Authentication.class))).thenReturn("dummyToken");
		jwtSettings = mock(JwtSettings.class);
		claims = mock(Claims.class);
		when(jwtSettings.getAuthHeaderKey()).thenReturn("Authorization");
		when(jwtTokenFactory.validateToken(Mockito.anyString())).thenReturn(true);
		when(jwtTokenFactory.parseToken(Mockito.anyString())).thenReturn(claims);
		filter = new JWTAuthorizationFilter(manager,jwtSettings,jwtTokenFactory);
	}

	@After
	public void clearContext() throws Exception {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testFilterIgnoresRequestsContainingNoAuthorizationHeader() throws Exception {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/test");
		final MockHttpServletResponse response = new MockHttpServletResponse();

		FilterChain chain = mock(FilterChain.class);
		filter.doFilter(request, response, chain);

		verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));

		// Test
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
	}

	@Test
	public void testNormalOperation() throws Exception {
		String token = jwtTokenFactory.generateToken(authentication);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization", "Bearer " + token);
		request.setServletPath("/test");
		when(claims.getSubject()).thenReturn("USERNAME");
		when(claims.get("userId")).thenReturn("userId");
		when(claims.get("roles")).thenReturn("READ,WRITE");

		// Test
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
		FilterChain chain = mock(FilterChain.class);
		filter.doFilter(request, new MockHttpServletResponse(), chain);

		verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
		assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("USERNAME");
	}

	@Test(expected = InsufficientAuthenticationException.class)
	public void testInvalidToken() throws Exception {
		String token = jwtTokenFactory.generateToken(authentication);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization", "Bearer " + token.substring(0, token.length() - 2));
		request.setServletPath("/test");

		// Test
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
		final MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain chain = mock(FilterChain.class);
		filter.doFilter(request, response, chain);

		verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
	}

}
