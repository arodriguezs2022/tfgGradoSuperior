package org.jesuitasrioja.com.Hotel.modelo.pais;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pais")
public class Pais implements Serializable{
	
	@Id
	@Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String descripcion;
	
	@Lob
	@Column(name = "photo", columnDefinition="BLOB")
	private byte[] photo;

}
