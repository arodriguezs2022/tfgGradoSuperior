package org.jesuitasrioja.com.Hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ReservaNoEncontradoException extends RuntimeException{
	public ReservaNoEncontradoException(Integer idReserva) {
		super("reserva with " + idReserva + " can not be retrieved");
	}
}
