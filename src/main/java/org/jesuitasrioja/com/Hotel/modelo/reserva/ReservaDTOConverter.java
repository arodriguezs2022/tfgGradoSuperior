package org.jesuitasrioja.com.Hotel.modelo.reserva;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservaDTOConverter {
	@Autowired
	private final ModelMapper modelMapper;

	public ReservaDTOConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public ReservaDTO convertReservaToReservaDTO(Reserva reserva) {
		
		ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);
		
		return dto;
	}
}
