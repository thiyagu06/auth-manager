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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.izettle.authmanagement.auth.jwt.JwtTokenUtil;
import com.izettle.authmanagement.dto.login.LoggedInUserDetails;
import com.izettle.authmanagement.filters.JWTAuthorizationFilter;

public class JwtAuthenticationFilterTest {

	private AuthenticationManager manager;

	private JWTAuthorizationFilter filter;

	private Authentication authentication;

	@Before
	public void setUp() throws Exception {
		LoggedInUserDetails principal = new LoggedInUserDetails("USERNAME", "", new ArrayList<>(), "123445454545");
		UsernamePasswordAuthenticationToken rodRequest = new UsernamePasswordAuthenticationToken(principal, "testUser");
		rodRequest.setDetails(new WebAuthenticationDetails(new MockHttpServletRequest()));
		authentication = new UsernamePasswordAuthenticationToken(principal, "koala", new ArrayList<>());

		manager = mock(AuthenticationManager.class);
		when(manager.authenticate(rodRequest)).thenReturn(authentication);

		filter = new JWTAuthorizationFilter(manager);
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
		String token = JwtTokenUtil.generateToken(authentication);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization", "Bearer " + token);
		request.setServletPath("/test");

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
		String token = JwtTokenUtil.generateToken(authentication);
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
