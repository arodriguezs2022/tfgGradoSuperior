package org.jesuitasrioja.com.Hotel.controllers;

import org.jesuitasrioja.com.Hotel.configurations.security.jwt.JwtTokenProvider;
import org.jesuitasrioja.com.Hotel.modelo.user.GetUserDTO;
import org.jesuitasrioja.com.Hotel.modelo.user.JwtUserResponse;
import org.jesuitasrioja.com.Hotel.modelo.user.LoginRequest;
import org.jesuitasrioja.com.Hotel.modelo.user.UserDTOConverter;
import org.jesuitasrioja.com.Hotel.modelo.user.UserEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationManager authenticationManager;
	// para poder generar el token
	private final JwtTokenProvider tokenProvider;
	// converter si utilizamos DTO en nuestro caso no hará falta
	private final UserDTOConverter converter;

	@PostMapping("/auth/login")
	// se puede devolver un ResponseEntity con el JwtUserResponse
	public JwtUserResponse login(@RequestBody LoginRequest loginRequest) {

		// crearemos un authentication con los argumentos recibidos de la petición
		// si está autenticado nos creará el authentication.
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()

				));

		// guardamos en el contexto de seguridad si se ha logueado
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// crearemos la respuesta con su token correspondiente.
		UserEntity user = (UserEntity) authentication.getPrincipal();
		String jwtToken = tokenProvider.generateToken(authentication);

		// devolveremos el jwtUserResponse creado utilizando la siguiente función por
		// ejemplo.
		return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/user/me")
	public GetUserDTO me(@AuthenticationPrincipal UserEntity user) {
		// devolveremos el user autenticado.
		return converter.convertUserEntityToGetUserDto(user);
	}

	private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(UserEntity user, String jwtToken) {
		return JwtUserResponse.jwtUserResponseBuilder().username(user.getUsername()).roles(user.getRoles()).token(jwtToken).build();
	}
}
