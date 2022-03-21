package org.jesuitasrioja.com.Hotel.modelo.pais;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelDTOConverter {
	@Autowired
	private final ModelMapper modelMapper;

	public HotelDTOConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public HotelDTO convertPaisToPaisDTO(Hotel hotel) {
		
		HotelDTO dto = modelMapper.map(hotel, HotelDTO.class);
		return dto;
	}
}
