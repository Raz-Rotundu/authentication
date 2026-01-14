package com.example.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author Razvan
 * Security configuration class
 * Only want to create users and issue tokens
 */
@Configuration
public class SecurityConfiguration {
	
	/**
	 * Creates a passwordEncoder bean to be used when hashing received passwords
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Creates a DefaultSecurityFilterChain bean.
	 * Disable CSRF and CORS filters
	 * /users/register and  /users/token endpoints are visible to all, all other endpoints accessible to authenticared users
	 * @param http the http session?
	 * @return A configured DefaultSecurityFilterChain object
	 * @throws Exception
	 */
	@Bean
	protected DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth ->
						auth.requestMatchers(
								"/users/register",
								"/users/token").permitAll()
						.anyRequest().authenticated()
				)
				.sessionManagement(sess -> sess.
						sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
				
	}
}
