package com.idat.currulo.web.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCategoria;
	
	@Size(max = 30)
	@Column(nullable = false, unique = true)
	private String nomCategoria;
	
	@Column(nullable = false, unique = false)
	private Boolean estado;
	
	@OneToMany(mappedBy = "categoria")
	private Collection<Plato> itemsPlatos = new ArrayList<>();
	
	@PrePersist
	public void estadoCategoria() {
		estado = true;
	}
	
	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNomCategoria() {
		return nomCategoria;
	}

	public void setNomCategoria(String nomCategoria) {
		this.nomCategoria = nomCategoria;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Collection<Plato> getItemsPlatos() {
		return itemsPlatos;
	}

	public void setItemsPlatos(Collection<Plato> itemsPlatos) {
		this.itemsPlatos = itemsPlatos;
	}
		
}