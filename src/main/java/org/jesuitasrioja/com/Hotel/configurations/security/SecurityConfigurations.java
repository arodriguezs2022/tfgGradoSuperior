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
				.antMatchers(HttpMethod.GET, "/reservas/**").permitAll()  
				.antMatchers(HttpMethod.GET, "/reserva/**").permitAll() 
				.antMatchers(HttpMethod.POST, "/reserva/**").permitAll() 
				.antMatchers(HttpMethod.PUT, "/reserva/**").permitAll() 
				.antMatchers(HttpMethod.DELETE, "/reserva/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/usuarios/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/usuario/**").permitAll() 
				.antMatchers(HttpMethod.POST, "/usuario/**").permitAll() 
				.antMatchers(HttpMethod.PUT, "/usuario/**").permitAll()
				.antMatchers(HttpMethod.DELETE, "/usuario/**").permitAll() // borrar un alumno
				.anyRequest().permitAll().and().csrf().disable();

		// Añadimos el filtro (lo hacemos más adelante). Justo antes de
		// UsernamePasswordAuthenticationFilter.class.
		http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
