package org.jesuitasrioja.com.Hotel.modelo.reserva;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
	private String nombre;
	private Date fechaEntrada;
	private Date fechaSalida;
	private String paisOrigen;	
	private String horario;
	private Boolean tranfer;
	private String telefono;
	private String email;
	private String totalDinero;
}
