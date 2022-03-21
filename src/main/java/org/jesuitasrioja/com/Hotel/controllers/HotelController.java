package org.jesuitasrioja.com.Hotel.controllers;

import java.util.Optional;
import java.util.function.Function;

import org.jesuitasrioja.com.Hotel.modelo.pais.Hotel;
import org.jesuitasrioja.com.Hotel.modelo.pais.HotelDTO;
import org.jesuitasrioja.com.Hotel.modelo.pais.HotelDTOConverter;
import org.jesuitasrioja.com.Hotel.persistencia.services.HotelService;
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
public class HotelController {
	
	@Autowired
	private HotelService hs;
	
	@Autowired
	HotelDTOConverter hotelDTOConverter;
	
	
	/*
	 * 
	 * GET paises:
	 * 
	 */

	@ApiOperation(value = "Obtener todos los hoteles paginados", notes = "Con este metodo conseguimos mandar todos los hoteles de 10 en 10. Así la Web podrá recoger los datos mas facilmente.")
	@GetMapping("/hoteles")
	public ResponseEntity<?> allHotels(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<Hotel> pagina = hs.findAll(pageable);

		// transformar elementos de la pagina a DTO
		Page<HotelDTO> paginaDTO = pagina.map(new Function<Hotel, HotelDTO>() {
			@Override
			public HotelDTO apply(Hotel h) {
				return hotelDTOConverter.convertPaisToPaisDTO(h);
			}
		});

		return ResponseEntity.status(HttpStatus.OK).body(paginaDTO);
	}

	/*
	 * 
	 * GET hotel/{idHotel}
	 * 
	 */

	@ApiOperation(value = "Obtener un hotel por identificador", notes = "Con este metodo conseguimos recoger la información de un Hotel específico.")
	@GetMapping("/hotel/{id}")
	public ResponseEntity<Hotel> getHotel(@PathVariable Integer id) {

		Optional<Hotel> hotelOptional = hs.findById(id);
		if (hotelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(hotelOptional.get());
		} else {
			throw new HotelNoEncontradoException(id);
		}
	}

	/*
	 * 
	 * POST hotel
	 * 
	 */

	@ApiOperation(value = "Añadir un hotel", notes = "Con este metodo conseguimos añadir un Hotel.")
	@PostMapping("/hotel")
	public ResponseEntity<?> postHotel(@RequestBody Hotel nuevoHotel) {

		Hotel h = new Hotel();
		
		h.setDescripcion(nuevoHotel.getDescripcion());
		h.setPhoto(nuevoHotel.getPhoto());
		
		Optional<Hotel> hotelOptional = hs.findById(h.getId());
		
		if(hotelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El hotel que intentas añadir ya esta añadido");
		} else {
			hs.save(h);
			return ResponseEntity.status(HttpStatus.OK).body("");
		}
	}

	/*
	 * 
	 * PUT hotel
	 * 
	 */

	@ApiOperation(value = "Modificar un hotel", notes = "Con este metodo conseguimos modificar un Hotel.")
	@PutMapping("/hotel")
	public ResponseEntity<?> putHotel(@RequestBody Hotel editadaHotel) {
		return ResponseEntity.status(HttpStatus.OK).body(hs.edit(editadaHotel));
	}

	/*
	 * 
	 * DELETE hotel/{idHotel}
	 * 
	 **/

	@ApiOperation(value = "Borrar un hotel", notes = "Con este metodo conseguimos borrar un Hotel por identificador. De esta forma conseguiremos borrar un Hotel específico.")
	@DeleteMapping("/pais/{id}")
	public ResponseEntity<Hotel> deleteHotel(@PathVariable Integer id) {
		Hotel h = hs.findById(id).get();
		hs.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(h);
	}

}
