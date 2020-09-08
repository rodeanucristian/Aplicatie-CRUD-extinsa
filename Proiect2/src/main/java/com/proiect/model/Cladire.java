package com.proiect.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "cladiri")
public class Cladire implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Size(min=2, message = "Numele trebuie sa contina cel putin 2 caractere")
	private String nume;

	private String descriere;

	@Positive
	private int numar_etaje;

	@JsonManagedReference
	@OneToMany(mappedBy = "cladire", cascade = CascadeType.REMOVE)
	private Set<Camera> camere;

	public Set<Camera> getCamere() {
		return camere;
	}

	public void setCamere(Set<Camera> camere) {
		this.camere = camere;
	}

	@Override
	public String toString() {
		return "Cladire [id=" + id + ", nume=" + nume + ", descriere=" + descriere + ", numar_etaje=" + numar_etaje
				+ ", getId()=" + getId() + ", getNume()=" + getNume() + ", getDescriere()=" + getDescriere()
				+ ", getNumar_etaje()=" + getNumar_etaje() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}

	public int getNumar_etaje() {
		return numar_etaje;
	}

	public void setNumar_etaje(int numar_etaje) {
		this.numar_etaje = numar_etaje;
	}

}
