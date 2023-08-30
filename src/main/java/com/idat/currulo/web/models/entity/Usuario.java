package com.idat.currulo.web.models.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "nomUsuario"))
public class Usuario implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	
	@Size(max = 15)
	@Column(nullable = false)
	private String nomUsuario;
	
	@Size(max = 60)
	@Column(nullable = false, unique = false)
	private String password;
	
	@Column(nullable = false, unique = false)
	private Boolean estado;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "usuarios_roles", joinColumns = 
				@JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario"),
				inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "idRol"))
	private Collection<Rol> roles;
	
	@OneToOne(mappedBy = "usuario")
	private Cliente cliente;
	
	@OneToOne(mappedBy = "usuario")
	private Empleado empleado;
	
	public Usuario(Integer idUsuario, String nomUsuario, String password, Boolean estado,
					Collection<Rol> roles) {
		this.idUsuario = idUsuario;
		this.nomUsuario = nomUsuario;
		this.password = password;
		this.estado = estado;
		this.roles = roles;
	}

	public Usuario(String nomUsuario, String password, Boolean estado, Collection<Rol> roles) {
		this.nomUsuario = nomUsuario;
		this.password = password;
		this.estado = estado;
		this.roles = roles;
	}

	public Usuario() {
		
	}
	
	@PrePersist
	public void estadoUsuario() {
		estado = true;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Collection<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Rol> roles) {
		this.roles = roles;
	}

}