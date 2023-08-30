package com.idat.currulo.web.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "roles")
public class Rol implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRol;
	
	@Size(max = 20)
	@Column(nullable = false, unique = true)
	private String nomRol;
	
	@Column(nullable = false, unique = false)
	private Boolean estado;
	
	public Rol(Integer idRol, String nomRol, Boolean estado) {
		this.idRol = idRol;
		this.nomRol = nomRol;
		this.estado = estado;
	}
	
	public Rol(String nomRol) {
		this.nomRol = nomRol;
	}
	
	public Rol() {
		
	}
	
	@PrePersist
	public void estadoRol() {
		estado = true;
	}
	
	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getNomRol() {
		return nomRol;
	}

	public void setNomRol(String nomRol) {
		this.nomRol = nomRol;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}