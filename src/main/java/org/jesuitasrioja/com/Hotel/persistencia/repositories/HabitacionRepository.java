package org.jesuitasrioja.com.Hotel.persistencia.repositories;

import org.jesuitasrioja.com.Hotel.modelo.habitacion.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer>{

}
