package org.jesuitasrioja.com.Hotel.persistencia.repositories;

import org.jesuitasrioja.com.Hotel.modelo.reserva.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer>{

}
