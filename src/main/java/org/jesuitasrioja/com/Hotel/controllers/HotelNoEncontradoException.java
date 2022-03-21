package org.jesuitasrioja.com.Hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HotelNoEncontradoException extends RuntimeException{
	public HotelNoEncontradoException(Integer idPais) {
		super("Pais with " + idPais + " can not be retrieved");
	}
}
