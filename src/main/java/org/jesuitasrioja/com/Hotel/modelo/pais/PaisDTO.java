package org.jesuitasrioja.com.Hotel.modelo.pais;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaisDTO {

	private Integer id;
	private String descripcion;
	private byte[] photo;

}
