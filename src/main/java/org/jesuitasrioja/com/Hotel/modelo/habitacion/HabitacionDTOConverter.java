package org.jesuitasrioja.com.Hotel.modelo.habitacion;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HabitacionDTOConverter {
	@Autowired
	private final ModelMapper modelMapper;

	public HabitacionDTOConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public HabitacionDTO convertHabitacionToHabitacionDTO(Habitacion habitacion) {
			
		HabitacionDTO dto = modelMapper.map(habitacion, HabitacionDTO.class);
		return dto;
	}
}
