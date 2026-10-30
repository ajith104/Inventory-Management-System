package com.inventorymanagement.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.inventorymanagement.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter=jwtAuthenticationFilter;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

    http
    	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
        	    .requestMatchers(
        	        org.springframework.http.HttpMethod.OPTIONS, "/**"
        	    ).permitAll()

        	    .requestMatchers(
        	        "/auth/login",
        	        "/auth/setup-owner"
        	    ).permitAll()
        	    
        	    .requestMatchers(
            	        org.springframework.http.HttpMethod.GET,
            	        "/products/inventory-value").hasAnyRole("OWNER","MANAGER")
        	    
        	    .requestMatchers(
            	        org.springframework.http.HttpMethod.POST,
            	        "/products/add").hasAnyRole("OWNER","MANAGER")
        	    
        	    .requestMatchers(
            	        org.springframework.http.HttpMethod.PUT,
            	        "/products/**").hasAnyRole("OWNER","MANAGER")
        	    .requestMatchers(
            	        org.springframework.http.HttpMethod.DELETE,
            	        "/products/**").hasAnyRole("OWNER","MANAGER")
        	    
        	    .requestMatchers(
            	        org.springframework.http.HttpMethod.PUT,
            	        "/sales/**").hasAnyRole("OWNER","MANAGER")
        	    .requestMatchers(
            	        org.springframework.http.HttpMethod.DELETE,
            	        "/sales/**").hasAnyRole("OWNER","MANAGER")
        	    
        	    .requestMatchers(
        	        org.springframework.http.HttpMethod.GET,
        	        "/users",
        	        "/users/**"
        	    ).hasAnyRole("OWNER", "MANAGER")
        	    .requestMatchers(
        	    	    org.springframework.http.HttpMethod.DELETE,
        	    	    "/users/**"
        	    	).hasAnyRole("OWNER", "MANAGER")
        	    .requestMatchers(
        	    	    org.springframework.http.HttpMethod.PUT,
        	    	    "/users/**"
        	    	).hasAnyRole("OWNER", "MANAGER")

        	    .requestMatchers(
        	        org.springframework.http.HttpMethod.POST,
        	        "/auth/register"
        	    ).authenticated()

        	    .requestMatchers("/users/**").denyAll()

        	    .requestMatchers("/purchases","/purchases/**")
    	    		.hasAnyRole("OWNER","MANAGER")
    	    	
    	    	.requestMatchers("/suppliers","/suppliers/**")
    	    		.hasAnyRole("OWNER","MANAGER")
    	    		
    	    		.requestMatchers(
                	        org.springframework.http.HttpMethod.GET,
                	        "/dashboard/total-items-purchased").hasAnyRole("OWNER","MANAGER")
    	    	
        	    .anyRequest().authenticated()
        	)
        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        );

    return http.build();
}

	

	@Bean
		CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration configuration = new CorsConfiguration();

			configuration.setAllowedOrigins(
    	    List.of("http://localhost:4200")
			);
    
			configuration.setAllowedMethods(
				List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
			);

		configuration.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source =
			new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
