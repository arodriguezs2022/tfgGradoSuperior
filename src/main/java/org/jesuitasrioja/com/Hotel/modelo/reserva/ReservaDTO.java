package org.jesuitasrioja.com.Hotel.modelo.reserva;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
	private Date fechaEntrada;
	private Date fechaSalida;
}
