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
@Table(name = "identificaciones")
public class TipoIdentificacion implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codIdent;
	
	@Size(max = 30)
	@Column(nullable = false, unique = true)
	private String tipoIdent;
	
	@Column(nullable = false, unique = false)
	private Boolean estado;
	
	@OneToMany(mappedBy = "tipoidentificacion")
	private Collection<Cliente> itemsClientes = new ArrayList<>();
	
	@OneToMany(mappedBy = "tipoidentificacion")
	private Collection<Proveedor> itemsProveedores = new ArrayList<>();
	
	@OneToMany(mappedBy = "tipoidentificacion")
	private Collection<Empleado> itemsEmpleados = new ArrayList<>();
	
	@PrePersist
	public void estadoTipoIdentificacion() {
		estado = true;
	}
	
	public Integer getCodIdent() {
		return codIdent;
	}

	public void setCodIdent(Integer codIdent) {
		this.codIdent = codIdent;
	}

	public String getTipoIdent() {
		return tipoIdent;
	}

	public void setTipoIdent(String tipoIdent) {
		this.tipoIdent = tipoIdent;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Collection<Cliente> getItemsClientes() {
		return itemsClientes;
	}

	public void setItemsClientes(Collection<Cliente> itemsClientes) {
		this.itemsClientes = itemsClientes;
	}

	public Collection<Proveedor> getItemsProveedores() {
		return itemsProveedores;
	}

	public void setItemsProveedores(Collection<Proveedor> itemsProveedores) {
		this.itemsProveedores = itemsProveedores;
	}

	public Collection<Empleado> getItemsEmpleados() {
		return itemsEmpleados;
	}

	public void setItemsEmpleados(Collection<Empleado> itemsEmpleados) {
		this.itemsEmpleados = itemsEmpleados;
	}
		
}