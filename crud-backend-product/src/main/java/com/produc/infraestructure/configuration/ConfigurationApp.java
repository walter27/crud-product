package com.produc.infraestructure.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.produc.infraestructure.filter.FilterJWT;
import com.produc.infraestructure.models.ErrorDto;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class ConfigurationApp {

	@Value("${app.url.frontend}")
	String urlFrontend;

	@Value("${app.jwt.secret}")
	String jwtSecret;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, FilterJWT filterJWT,
			AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll()
						.requestMatchers("/products/**").authenticated()
						.anyRequest().permitAll())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))
				.addFilterBefore(filterJWT, UsernamePasswordAuthenticationFilter.class);
		http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
		return http.build();
	}

	@Bean
	public FilterJWT filterJWT() {
		return new FilterJWT(jwtSecret);
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
		return (request, response, exception) -> writeError(response, HttpStatus.UNAUTHORIZED,
				buildError("UNAUTHORIZED", exception.getMessage()), objectMapper);
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
		return (request, response, exception) -> writeError(response, HttpStatus.FORBIDDEN,
				buildError("FORBIDDEN", exception.getMessage()), objectMapper);
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(resolveAllowedOrigins());
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);
		config.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	private List<String> resolveAllowedOrigins() {
		List<String> configuredOrigins = Arrays.stream(urlFrontend.split(",")).map(String::trim)
				.filter(origin -> !origin.isBlank()).collect(Collectors.toList());
		if (configuredOrigins.isEmpty()) {
			throw new IllegalStateException("La propiedad app.url.frontend es obligatoria para configurar CORS");
		}
		return configuredOrigins;
	}
	
	@Bean
	ObjectMapper objectMapper() {
		return JsonMapper.builder().propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).build();
	}

	private ErrorDto buildError(String code, String message) {
		return ErrorDto.builder().code(code).message(message).details(List.of()).timestamp(LocalDateTime.now()).build();
	}

	private void writeError(HttpServletResponse response, HttpStatus status, ErrorDto error, ObjectMapper objectMapper)
			throws java.io.IOException {
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.writeValue(response.getWriter(), error);
	}
}
