package org.jesuitasrioja.com.Hotel.modelo.reserva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
	
	private Integer id;
	private String nombre;
	private String fechaEntrada;
	private String fechaSalida;
	private String paisOrigen;
	private String telefono;
	private String email;

}
