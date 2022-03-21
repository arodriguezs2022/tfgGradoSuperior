package org.jesuitasrioja.com.Hotel.persistencia.repositories;

import org.jesuitasrioja.com.Hotel.modelo.pais.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}
