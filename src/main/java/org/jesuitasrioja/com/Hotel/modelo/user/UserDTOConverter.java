package org.jesuitasrioja.com.Hotel.modelo.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {
	@Autowired
	private ModelMapper modelMapper;

	public UserDTOConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public GetUserDTO convertUserEntityToGetUserDto(UserEntity user) {
		return modelMapper.map(user, GetUserDTO.class);
	}
}