package org.jesuitasrioja.com.Hotel.modelo.pais;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaisDTOConverter {
	@Autowired
	private final ModelMapper modelMapper;

	public PaisDTOConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public PaisDTO convertPaisToPaisDTO(Pais pais) {
		
		PaisDTO dto = modelMapper.map(pais, PaisDTO.class);
		return dto;
	}
}
