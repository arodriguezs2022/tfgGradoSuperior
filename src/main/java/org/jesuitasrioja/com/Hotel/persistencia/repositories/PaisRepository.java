package org.jesuitasrioja.com.Hotel.persistencia.repositories;

import org.jesuitasrioja.com.Hotel.modelo.pais.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

}
