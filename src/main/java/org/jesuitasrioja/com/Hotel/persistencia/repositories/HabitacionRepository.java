package org.jesuitasrioja.com.Hotel.persistencia.repositories;

import java.util.Date;
import java.util.List;

import org.jesuitasrioja.com.Hotel.modelo.habitacion.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer>{
	@Query(value = "SELECT h FROM habitaciones h WHERE NOT EXISTS (SELECT NULL FROM reserva r WHERE r.habitacion = h.id AND :fecha2 <= r.fecha_salida AND :fecha1 >= r.fecha_entrada)", nativeQuery = true)
	List<Habitacion> getRooms(@Param("fecha1") Date fechaEntrada, @Param("fecha2") Date fechaSalida);
}
