package com.proiect.model;



import java.io.Serializable;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;



@Entity
@Table(name = "usi")
public class Usa implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	
	@Transient
	private Integer camera1_id;
	
	
	@Transient
	private Integer camera2_id;
	
	@Size(min=2, message = "Numele trebuie sa contina cel putin 2 caractere")
	private String nume;
	
	@Size(min=2, message="Adauga un exterior")
	private String exterior;
	

	@ManyToOne
	private Camera camera1;
	
	@ManyToOne
	private Camera camera2;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCamera1_id() {
		return camera1_id;
	}

	public void setCamera1_id(Integer camera1_id) {
		this.camera1_id = camera1_id;
	}

	public Integer getCamera2_id() {
		return camera2_id;
	}

	public void setCamera2_id(Integer camera2_id) {
		this.camera2_id = camera2_id;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getExterior() {
		return exterior;
	}

	public void setExterior(String exterior) {
		this.exterior = exterior;
	}

	public Camera getCamera1() {
		return camera1;
	}

	public void setCamera1(Camera camera1) {
		this.camera1 = camera1;
	}

	public Camera getCamera2() {
		return camera2;
	}

	public void setCamera2(Camera camera2) {
		this.camera2 = camera2;
	}







	
	
	
	

	
}
