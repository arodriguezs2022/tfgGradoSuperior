package org.jesuitasrioja.com.Hotel.controllers;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import org.jesuitasrioja.com.Hotel.modelo.habitacion.Habitacion;
import org.jesuitasrioja.com.Hotel.modelo.habitacion.HabitacionDTO;
import org.jesuitasrioja.com.Hotel.modelo.habitacion.HabitacionDTOConverter;
import org.jesuitasrioja.com.Hotel.modelo.reserva.ReservaDTO;
import org.jesuitasrioja.com.Hotel.persistencia.services.HabitacionService;
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
public class HabitacionController {
	
	@Autowired
	private HabitacionService hs;
	
	@Autowired
	HabitacionDTOConverter habitacionDTOConverter;

	/*
	 * 
	 * GET habitaciones:
	 * 
	 */

	@ApiOperation(value = "Obtener todas las habitaciones paginadas", notes = "Con este metodo conseguimos mandar todos los paises de 10 en 10. Así la Web podrá recoger los datos mas facilmente.")
	@GetMapping("/habitaciones")
	public ResponseEntity<?> allHabitaciones(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<Habitacion> pagina = hs.findAll(pageable);

		// transformar elementos de la pagina a DTO
		Page<HabitacionDTO> paginaDTO = pagina.map(new Function<Habitacion, HabitacionDTO>() {
			@Override
			public HabitacionDTO apply(Habitacion h) {
				return habitacionDTOConverter.convertHabitacionToHabitacionDTO(h);
			}
		});

		return ResponseEntity.status(HttpStatus.OK).body(paginaDTO);
	}
	
	@ApiOperation(value = "Obtener una habitacion por identificador", notes = "Con este metodo conseguimos recoger la información de una Habitacion específica.")
	@PostMapping("/habitaciones")
	public ResponseEntity<?> getHabitacionesDisponibles(@RequestBody ReservaDTO fechas) {
		
		Date fechaEntrada = fechas.getFechaEntrada();
		Date fechaSalida = fechas.getFechaSalida();
		
		return ResponseEntity.status(HttpStatus.OK).body(hs.getRooms(fechaEntrada, fechaSalida));
	}

	/*
	 * 
	 * GET pais/{idHabitacion}
	 * 
	 */

	@ApiOperation(value = "Obtener una habitacion por identificador", notes = "Con este metodo conseguimos recoger la información de una Habitacion específica.")
	@GetMapping("/habitacion/{id}")
	public ResponseEntity<Habitacion> getHabitacion(@PathVariable Integer id) {

		Optional<Habitacion> habitacionOptional = hs.findById(id);
		if (habitacionOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(habitacionOptional.get());
		} else {
			throw new HabitacionNoEncontradoException(id);
		}
	}

	/*
	 * 
	 * POST habitacion
	 * 
	 */

	@ApiOperation(value = "Añadir una habitacion", notes = "Con este metodo conseguimos añadir una Habitación.")
	@PostMapping("/habitacion")
	public ResponseEntity<?> postHabitacion(@RequestBody Habitacion nuevaHabitacion) {

		Habitacion h = new Habitacion();
		
		h.setNombre(nuevaHabitacion.getNombre());
		h.setDescripcion(nuevaHabitacion.getDescripcion());
		h.setPhoto(nuevaHabitacion.getPhoto());
		h.setPrecio(nuevaHabitacion.getPrecio());
		h.setTipo(nuevaHabitacion.getTipo());
		h.setHuespedes(nuevaHabitacion.getHuespedes());
	
		hs.save(h);
		return ResponseEntity.status(HttpStatus.OK).body("");
	
	}
	
	

	/*
	 * 
	 * PUT habitacion
	 * 
	 */

	@ApiOperation(value = "Modificar una habitacion", notes = "Con este metodo conseguimos modificar una Habitacion.")
	@PutMapping("/habitacion")
	public ResponseEntity<?> putHabitacion(@RequestBody Habitacion editadaHabitacion) {
		return ResponseEntity.status(HttpStatus.OK).body(hs.edit(editadaHabitacion));
	}

	/*
	 * 
	 * DELETE habitacion/{idHabitacion}
	 * 
	 **/

	@ApiOperation(value = "Borrar una habitacion", notes = "Con este metodo conseguimos borrar una Habitacion por identificador. De esta forma conseguiremos borrar una Habitacion específica.")
	@DeleteMapping("/habitacion/{id}")
	public ResponseEntity<Habitacion> deleteHabitacion(@PathVariable Integer id) {
		Habitacion h = hs.findById(id).get();
		hs.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(h);
	}
}
