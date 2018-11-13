package com.izettle.authmanagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Validator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.izettle.authmanagement.dto.user.User;
import com.izettle.authmanagement.dto.user.UserCredential;
import com.izettle.authmanagement.dto.user.UserRegistration;
import com.izettle.authmanagement.service.RegistrationService;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

	@MockBean
	private RegistrationService registrationService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BCryptPasswordEncoder BCryptPasswordEncoder;
	
	@MockBean
	@Qualifier("passwordValidator")
	private Validator passwordValidator;
	
	@MockBean
	@Qualifier("uniqueEmailValidator")
	private Validator uniqueEmailValidator;

	@Before
	public void setup() {
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.setMixInAnnotation(UserCredential.class, UserRegistionTestDto.class);
		objectMapper.registerModule(simpleModule);
		Mockito.when(passwordValidator.supports(Mockito.any())).thenReturn(true);
		Mockito.when(uniqueEmailValidator.supports(Mockito.any())).thenReturn(true);
	}

	@Test
	public void testCreateRegistrationFailsWithNullData() throws Exception {

		String expectedJson = "{\"errors\":[{\"field\":\"userCredential\",\"message\":\"user credential must not be null\"},{\"field\":\"userDetails\",\"message\":\"user details must not be null\"}]}";
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(new UserRegistration());
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsString(new UserRegistration()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(expectedJson));
	}

	@Test
	public void testCreateRegistrationSuccess() throws Exception {
		UserRegistration userRegistration = createUserRegistrationRequest();
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(userRegistration);
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsBytes(userRegistration))
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());
	}

	private UserRegistration createUserRegistrationRequest() {
		UserRegistration userRegistration = new UserRegistration();
		User user = new User();
		user.setFirstName("Thiyagu");
		user.setLastName("GK6");
		user.setEmail("thiyagu103@gmail.com");
		UserCredential userCredential = new UserCredential();
		userCredential.setPassword("1234567");
		userCredential.setConfirmPassword("123457");
		userRegistration.setUserCredential(userCredential);
		userRegistration.setUserDetails(user);
		return userRegistration;
	}

	@Test
	public void testCreateRegistrationEmailRequired() throws JsonProcessingException, Exception {
		String expectedJson = "{\"errors\":[{\"field\":\"userDetails.email\",\"message\":\"email should not be empty\"}]}";
		UserRegistration userRegistration = createUserRegistrationRequest();
		userRegistration.getUserDetails().setEmail("");
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(userRegistration);
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsBytes(userRegistration))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(expectedJson));
	}

	@Test
	public void testCreateRegistrationInvalidEmail() throws JsonProcessingException, Exception {
		String expectedJson = "{\"errors\":[{\"field\":\"userDetails.email\",\"message\":\"invalid email format\"}]}";
		UserRegistration userRegistration = createUserRegistrationRequest();
		userRegistration.getUserDetails().setEmail("thiyagu103gmail.com");
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(userRegistration);
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsBytes(userRegistration))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(expectedJson));
	}

	@Test
	public void testCreateRegistrationLastNameLessThan3Characters() throws JsonProcessingException, Exception {
		String expectedJson = "{\"errors\":[{\"field\":\"userDetails.lastName\",\"message\":\"Last name must be minimum 3 characters and maximum 20 characters\"}]}";
		UserRegistration userRegistration = createUserRegistrationRequest();
		userRegistration.getUserDetails().setLastName("GK");
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(userRegistration);
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsBytes(userRegistration))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(expectedJson));
	}

	@Test
	public void testCreateRegistrationFirstNameLessThan3Characters() throws JsonProcessingException, Exception {
		String expectedJson = "{\"errors\":[{\"field\":\"userDetails.firstName\",\"message\":\"First name must be minimum 3 characters and maximum 20 characters\"}]}";
		UserRegistration userRegistration = createUserRegistrationRequest();
		userRegistration.getUserDetails().setFirstName("GK");
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(userRegistration);
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsBytes(userRegistration))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(expectedJson));
	}

	@Test
	public void testCreateRegistrationPasswordLessThan6Characters() throws JsonProcessingException, Exception {
		String expectedJson = "{\"errors\":[{\"field\":\"userCredential.password\",\"message\":\"password must be minimum 6 characters and maximum 12 characters\"}]}";
		UserRegistration userRegistration = createUserRegistrationRequest();
		userRegistration.getUserCredential().setPassword("1234");
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(userRegistration);
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsBytes(userRegistration))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(expectedJson));
	}

	@Test
	public void testCreateRegistrationInvalidEmailAndFirstnameMissing() throws JsonProcessingException, Exception {
		String expectedJson = "{\"errors\":[{\"field\":\"userDetails.firstName\",\"message\":\"First name must be minimum 3 characters and maximum 20 characters\"},{\"field\":\"userDetails.email\",\"message\":\"invalid email format\"},{\"field\":\"userDetails.firstName\",\"message\":\"firstname should not be empty\"}]}";
		UserRegistration userRegistration = createUserRegistrationRequest();
		userRegistration.getUserDetails().setEmail("thiyagu103gmail.com");
		userRegistration.getUserDetails().setFirstName("");
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(userRegistration);
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsBytes(userRegistration))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(expectedJson)).andDo(print());
	}

	@Test
	public void testCreateRegistrationLastNameMissing() throws JsonProcessingException, Exception {
		String expectedJson = "{\"errors\":[{\"field\":\"userDetails.lastName\",\"message\":\"Last name must be minimum 3 characters and maximum 20 characters\"},{\"field\":\"userDetails.lastName\",\"message\":\"lastname should not be empty\"}]}";
		UserRegistration userRegistration = createUserRegistrationRequest();
		userRegistration.getUserDetails().setLastName("");
		Mockito.when(registrationService.registerUser(Mockito.any(UserRegistration.class)))
				.thenReturn(userRegistration);
		mockMvc.perform(post("/register").content(objectMapper.writeValueAsBytes(userRegistration))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(expectedJson)).andDo(print());
	}

}

class UserRegistionTestDto {

	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	String password;
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	String confirmPassword;
}
