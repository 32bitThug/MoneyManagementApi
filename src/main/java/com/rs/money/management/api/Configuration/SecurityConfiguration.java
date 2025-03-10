package com.rs.money.management.api.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rs.money.management.api.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthFilter;

	// Explicitly define constructor
	public SecurityConfiguration(AuthenticationProvider authenticationProvider,
			JwtAuthenticationFilter jwtAuthFilter) {
		this.authenticationProvider = authenticationProvider;
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf
						.disable())
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/api/v1/auth/**")
						.permitAll()
						.anyRequest()
						.authenticated())
				.sessionManagement(management -> management
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}