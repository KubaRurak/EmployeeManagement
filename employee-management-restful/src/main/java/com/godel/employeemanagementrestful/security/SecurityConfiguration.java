package com.godel.employeemanagementrestful.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	

	@Autowired
    private JwtAuthEntryPoint authEntryPoint;
	@Autowired
	CorsConfigurationSource corsConfigurationSource;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {	
		http.authorizeHttpRequests(
				auth -> {
		            auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		            auth.requestMatchers("/api/v1/auth/login").permitAll();
					auth.requestMatchers(HttpMethod.GET, "/api/v1/payroll/**").hasAnyAuthority("ROLE_Admin", "ROLE_Engineer", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.GET, "/api/v1/statistics/**").hasAnyAuthority("ROLE_Admin", "ROLE_Operator");
					auth.requestMatchers("/api/v1/timetables/**").hasAnyAuthority("ROLE_Admin", "ROLE_Engineer", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyAuthority("ROLE_Admin", "ROLE_Engineer", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("ROLE_Admin");
					auth.requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority("ROLE_Admin", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.GET, "/api/v1/workorders/**").hasAnyAuthority("ROLE_Admin", "ROLE_Engineer", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.POST, "/api/v1/workorders/**").hasAnyAuthority("ROLE_Admin", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.PUT, "/api/v1/workorders/**").hasAnyAuthority("ROLE_Admin", "ROLE_Engineer", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.DELETE, "/api/v1/workorders/**").hasAnyAuthority("ROLE_Admin", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.GET, "/api/v1/statustypes/**").hasAnyAuthority("ROLE_Admin", "ROLE_Engineer", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.GET, "/api/v1/ordertypes/**").hasAnyAuthority("ROLE_Admin", "ROLE_Engineer", "ROLE_Operator");
					auth.requestMatchers(HttpMethod.POST, "/api/v1/ordertypes/**").hasAuthority("ROLE_Admin");
					auth.requestMatchers(HttpMethod.PUT, "/api/v1/ordertypes/**").hasAuthority("ROLE_Admin");					
					auth.anyRequest().authenticated();
				});
		http.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
//		http.httpBasic();
		http.csrf().disable();
		http.cors().configurationSource(corsConfigurationSource);
		http.headers().frameOptions().sameOrigin();
		http.exceptionHandling().authenticationEntryPoint(authEntryPoint);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public  JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

}