package org.jesuitasrioja.com.Hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HabitacionNoEncontradoException extends RuntimeException{
	public HabitacionNoEncontradoException(Integer idHabitacion) {
		super("Habitacion with " + idHabitacion + " can not be retrieved");
	}
}
