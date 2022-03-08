package org.jesuitasrioja.com.Hotel.controllers;

import java.util.Optional;
import java.util.function.Function;

import org.jesuitasrioja.com.Hotel.modelo.user.GetUserDTO;
import org.jesuitasrioja.com.Hotel.modelo.user.UserDTOConverter;
import org.jesuitasrioja.com.Hotel.modelo.user.UserEntity;
import org.jesuitasrioja.com.Hotel.persistencia.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class UserEntityController {
	@Autowired
	private UserEntityService ues;

	@Autowired
	UserDTOConverter userDTOConverter;

	/*
	 * 
	 * POST usuario/
	 * 
	 */
	@PostMapping("/usuario")
	public ResponseEntity<UserEntity> nuevoUsuario(@RequestBody UserEntity newUser) {
		try {
			// en caso de querer registrar un usuario cuyo nombre ya esté en el sistema
			// devolveremos un código de error 409 - Conflict.
			if (!ues.findByUserName(newUser.getUsername()).isPresent()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(ues.nuevoUsuario(newUser));
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/*
	 * 
	 * GET usuario/{id}
	 * 
	 */

	@GetMapping("/usuario/{id}")
	public ResponseEntity<UserEntity> getUser(@PathVariable String id) {

		Optional<UserEntity> userOptional = ues.findById(id);
		if (userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
		} else {
			throw new UserNoEncontrado(id);
		}
	}

	/*
	 * 
	 * GET usuarios/
	 * 
	 */
	@GetMapping("/usuarios")
	public ResponseEntity<?> getUsuarios(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		try {
			Page<UserEntity> pagina = ues.findAll(pageable);
			Page<GetUserDTO> paginaDTO = pagina.map(new Function<UserEntity, GetUserDTO>() {
				@Override
				public GetUserDTO apply(UserEntity u) {
					return userDTOConverter.convertUserEntityToGetUserDto(u);
				}
			});
			return ResponseEntity.status(HttpStatus.OK).body(paginaDTO);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/*
	 * 
	 * PUT usuarios
	 * 
	 */

	@PutMapping("/usuario")
	public ResponseEntity<UserEntity> putUsuario(@RequestBody UserEntity editadoUsuario) {
		return ResponseEntity.status(HttpStatus.OK).body(ues.edit(editadoUsuario));
	}

	/*
	 * 
	 * DELETE usuario/{id}
	 * 
	 **/

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<UserEntity> deleteUsuario(@PathVariable String id) {
		
		UserEntity u = ues.findById(id).get();
		
		ues.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(u);
	}
}
