package org.jesuitasrioja.com.Hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PaisNoEncontradoException extends RuntimeException{
	public PaisNoEncontradoException(Integer idPais) {
		super("Pais with " + idPais + " can not be retrieved");
	}
}
