package com.izettle.authmanagement.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.izettle.authmanagement.access.PermissionAuthority;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izettle.authmanagement.dto.login.LoginAttempt;
import com.izettle.authmanagement.auth.jwt.JwtSettings;
import com.izettle.authmanagement.auth.jwt.JwtTokenFactory;
import com.izettle.authmanagement.dto.login.LoggedInUserDetails;
import com.izettle.authmanagement.dto.user.UserRegistration;
import com.izettle.authmanagement.service.LoginAttemptService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LoginAttemptsController.class)
public class LoginAttemptsControllerTest {

	@MockBean
	private LoginAttemptService loginAttemptService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BCryptPasswordEncoder BCryptPasswordEncoder;
	
	@MockBean
	private JwtSettings jwtSettings;
	
	@MockBean
	private JwtTokenFactory jwtTokenFactory;

	@Before
	public void setupAuthentication() {
		final Collection authorities = Arrays.asList("READ","WRITE").stream().map(
			PermissionAuthority::new).collect(Collectors.toList());
		LoggedInUserDetails principal = new LoggedInUserDetails("USERNAME", "", authorities, "123445454545","US");
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(principal, "", authorities));
	}

	@Test
	public void accessBeforeLogin() throws Exception {
		SecurityContextHolder.clearContext();
		Mockito.when(loginAttemptService.getSuccessfulLoginAttempts(Mockito.anyString(), Mockito.any(Pageable.class)))
				.thenReturn(Collections.emptyList());
		mockMvc.perform(get("/loginAttempts/success").content(objectMapper.writeValueAsString(new UserRegistration()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
		Mockito.verifyZeroInteractions(loginAttemptService);
	}

	@Test
	public void TestEmptyResult() throws Exception {
		Mockito.when(loginAttemptService.getSuccessfulLoginAttempts(Mockito.anyString(), Mockito.any(Pageable.class)))
				.thenReturn(Collections.emptyList());
		mockMvc.perform(get("/loginAttempts/success").content(objectMapper.writeValueAsString(new UserRegistration()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize((0))));
	}

	@Test
	public void TestwithMaximumResults() throws Exception {
		Mockito.when(loginAttemptService.getSuccessfulLoginAttempts(Mockito.anyString(), Mockito.any(Pageable.class)))
				.thenReturn(getMockedResults());
		mockMvc.perform(get("/loginAttempts/success").content(objectMapper.writeValueAsString(new UserRegistration()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize((5))));
	}

	public List<LoginAttempt> getMockedResults() {
		List<LoginAttempt> loginAttempts = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			LoginAttempt loginAttempt = new LoginAttempt();
			loginAttempt.setLoggedInAt(LocalDateTime.now().plusHours(i));
			loginAttempts.add(loginAttempt);
		}
		return loginAttempts;
	}
}
