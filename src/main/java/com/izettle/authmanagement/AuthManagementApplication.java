package com.izettle.authmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Entry point for auth management application.
 * 
 * @author Thiyagu
 * @version 1.0
 * 
 */
@SpringBootApplication
public class AuthManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthManagementApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
