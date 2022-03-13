package org.jesuitasrioja.com.Hotel.controllers;

import java.util.Optional;
import java.util.function.Function;

import org.jesuitasrioja.com.Hotel.modelo.pais.Pais;
import org.jesuitasrioja.com.Hotel.modelo.pais.PaisDTO;
import org.jesuitasrioja.com.Hotel.modelo.pais.PaisDTOConverter;
import org.jesuitasrioja.com.Hotel.persistencia.services.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class PaisController {
	
	@Autowired
	private PaisService ps;
	
	@Autowired
	PaisDTOConverter paisDTOConverter;
	
	
	/*
	 * 
	 * GET paises:
	 * 
	 */

	@ApiOperation(value = "Obtener todos los paises paginados", notes = "Con este metodo conseguimos mandar todos los paises de 10 en 10. Así la Web podrá recoger los datos mas facilmente.")
	@GetMapping("/paises")
	public ResponseEntity<?> allPaises(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<Pais> pagina = ps.findAll(pageable);

		// transformar elementos de la pagina a DTO
		Page<PaisDTO> paginaDTO = pagina.map(new Function<Pais, PaisDTO>() {
			@Override
			public PaisDTO apply(Pais p) {
				return paisDTOConverter.convertPaisToPaisDTO(p);
			}
		});

		return ResponseEntity.status(HttpStatus.OK).body(paginaDTO);
	}

	/*
	 * 
	 * GET pais/{idPais}
	 * 
	 */

	@ApiOperation(value = "Obtener un pais por identificador", notes = "Con este metodo conseguimos recoger la información de un Pais específico.")
	@GetMapping("/pais/{id}")
	public ResponseEntity<Pais> getPais(@PathVariable Integer id) {

		Optional<Pais> paisOptional = ps.findById(id);
		if (paisOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(paisOptional.get());
		} else {
			throw new PaisNoEncontradoException(id);
		}
	}

	/*
	 * 
	 * POST pais
	 * 
	 */

	@ApiOperation(value = "Añadir un pais", notes = "Con este metodo conseguimos añadir un Pais.")
	@PostMapping("/pais")
	public ResponseEntity<?> postPais(@RequestBody Pais nuevoPais) {

		Pais p = new Pais();
		
		p.setDescripcion(nuevoPais.getDescripcion());
		p.setPhoto(nuevoPais.getPhoto());
		
		Optional<Pais> paisOptional = ps.findById(p.getId());
		
		if(paisOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El pais que intentas añadir ya esta añadida");
		} else {
			ps.save(p);
			return ResponseEntity.status(HttpStatus.OK).body("");
		}
	}

	/*
	 * 
	 * PUT pais
	 * 
	 */

	@ApiOperation(value = "Modificar un pais", notes = "Con este metodo conseguimos modificar un Pais.")
	@PutMapping("/pais")
	public ResponseEntity<?> putReserva(@RequestBody Pais editadaPais) {
		return ResponseEntity.status(HttpStatus.OK).body(ps.edit(editadaPais));
	}

	/*
	 * 
	 * DELETE pais/{idPais}
	 * 
	 **/

	@ApiOperation(value = "Borrar un pais", notes = "Con este metodo conseguimos borrar un Pais por identificador. De esta forma conseguiremos borrar un Pais específico.")
	@DeleteMapping("/pais/{id}")
	public ResponseEntity<Pais> deletePais(@PathVariable Integer id) {
		Pais p = ps.findById(id).get();
		ps.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(p);
	}

}
