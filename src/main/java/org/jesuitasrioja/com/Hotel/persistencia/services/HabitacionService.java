package org.jesuitasrioja.com.Hotel.persistencia.services;

import java.util.Date;
import java.util.List;

import org.jesuitasrioja.com.Hotel.modelo.habitacion.Habitacion;
import org.jesuitasrioja.com.Hotel.persistencia.repositories.HabitacionRepository;
import org.springframework.stereotype.Service;

@Service
public class HabitacionService extends BaseService<Habitacion, Integer, HabitacionRepository>{
	public List<Habitacion> getRooms(Date fechaEntrada, Date fechaSalida) {
		return this.repositorio.getRooms(fechaEntrada, fechaSalida);
	}
}
