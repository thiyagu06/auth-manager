package com.izettle.authmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.izettle.authmanagement.auth.CustomAuthenticationEntryPoint;
import com.izettle.authmanagement.filters.JWTAuthorizationFilter;
import com.izettle.authmanagement.filters.UsernameAndPasswordAuthenticationFilter;

/**
 * The security config for auth manager application.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 
	 * The user details service
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * The password encoder bean.
	 */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/error").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll().anyRequest().authenticated().and()
				.addFilter(new UsernameAndPasswordAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

}