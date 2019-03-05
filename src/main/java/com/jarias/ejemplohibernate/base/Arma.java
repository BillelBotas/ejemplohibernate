package com.jarias.ejemplohibernate.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="armas")
public class Arma {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="nombre")
	private String nombre;
	@Column(name="ataque")
	private int ataque;
	@Column(name="durabilidad")
	private int durabilidad;
	@ManyToOne
	@JoinColumn(name="id_personaje")
	private Personaje personaje;
	
	public Arma() {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getAtaque() {
		return ataque;
	}
	
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	
	public int getDurabilidad() {
		return durabilidad;
	}
	
	public void setDurabilidad(int durabilidad) {
		this.durabilidad = durabilidad;
	}

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Arma))
			return false;
		
		Arma arma = (Arma) o;
		if (nombre.equals(arma.getNombre()))
			return true;
		
		return false;
	}

}
