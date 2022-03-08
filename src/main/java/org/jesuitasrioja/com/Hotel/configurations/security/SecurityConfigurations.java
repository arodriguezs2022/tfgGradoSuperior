package org.jesuitasrioja.com.Hotel.configurations.security;


import org.jesuitasrioja.com.Hotel.configurations.security.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	private final AuthenticationEntryPoint jwtAuthenticationEntryPoint = null;
	private final UserDetailsService userDetailsService = null;
	private final PasswordEncoder passwordEncoder = null;
	private final JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter();

	@Bean
	@Override
	// Este método expone el mecanismo de autenticación para poder utilizarlo en un
	// filtro. Por eso lleva la anotación de Bean.
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/alumnos/**").permitAll() // ver todos los alumnos 
				.antMatchers(HttpMethod.GET, "/alumno/**").permitAll() // ver un determinado alumno
				.antMatchers(HttpMethod.GET, "/usuarios/**").permitAll() // ver todos los usuarios
				.antMatchers(HttpMethod.POST, "/alumno/**").permitAll() // añadir un alumno
				.antMatchers(HttpMethod.PUT, "/alumno/**").permitAll() // modificar un alumno
				.antMatchers(HttpMethod.DELETE, "/alumno/**").permitAll() // borrar un alumno
				.antMatchers(HttpMethod.POST, "/usuario/**").permitAll() // añadir un usuario
				.antMatchers(HttpMethod.PUT, "/usuario/**").permitAll() // modificar un alumno
				.antMatchers(HttpMethod.DELETE, "/usuario/**").permitAll() // borrar un alumno
				.anyRequest().permitAll().and().csrf().disable();

		// Añadimos el filtro (lo hacemos más adelante). Justo antes de
		// UsernamePasswordAuthenticationFilter.class.
		http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
