package org.jesuitasrioja.com.Hotel.modelo.habitacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitacionDTO {

	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer precio;
	private String photo;
	
}
