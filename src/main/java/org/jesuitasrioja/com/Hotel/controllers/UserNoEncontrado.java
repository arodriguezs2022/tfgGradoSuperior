package org.jesuitasrioja.com.Hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNoEncontrado extends RuntimeException{
	public UserNoEncontrado(String idAlumno) {
		super("alumno with " + idAlumno + " can not be retrieved");
	}
	public UserNoEncontrado(String idAlumno, String idProfesor) {
		super("alumno y profesor with " + idAlumno + " and " + idProfesor + " can not be retrieved");
	}
}
