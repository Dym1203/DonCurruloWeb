package com.idat.currulo.web.models.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "empleados")
public class Empleado implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEmpleado;
	
	@Size(max = 50)
	@Column(nullable = false, unique = false)
	private String nomEmp;
	
	@Size(max = 50)
	@Column(nullable = false, unique = false)
	private String apeEmp;
	
	@Size(max = 10)
	@Column(nullable = false, unique = false)
	private String sexo;
	
	@Size(max = 9)
	@Column(nullable = false, unique = true)
	private String telEmp;
	
	@Size(max = 50)
	@Column(nullable = false, unique = true)
	private String emailEmp;
	
	@Size(max = 30)
	@Column(nullable = false, unique = false)
	private String cargo;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	private LocalDate fechanacimiento;
	
	@ManyToOne
	@JoinColumn(name = "cod_ident", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition =
				"foreign key (cod_ident) references identificaciones(cod_ident)"))
	private TipoIdentificacion tipoidentificacion;
	
	@Size(max = 11)
	@Column(nullable = false, unique = true)
	private String numDocEmp;
	
	@OneToOne
	@JoinColumn(name = "id_usuario", nullable = false, unique = true,
				foreignKey = @ForeignKey(foreignKeyDefinition = 
				"foreign key (id_usuario) references usuarios (id_usuario)"))
	private Usuario usuario;
	
	@Column(nullable = false)
	private Double salario;
	
	private String imagen;
	
	@Column(nullable = false, unique = false)
	private Boolean estado;
	
	@PrePersist
	public void estadoEmpleado() {
		estado = true;
	}
	
	public Integer getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNomEmp() {
		return nomEmp;
	}

	public void setNomEmp(String nomEmp) {
		this.nomEmp = nomEmp;
	}

	public String getApeEmp() {
		return apeEmp;
	}

	public void setApeEmp(String apeEmp) {
		this.apeEmp = apeEmp;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelEmp() {
		return telEmp;
	}

	public void setTelEmp(String telEmp) {
		this.telEmp = telEmp;
	}

	public String getEmailEmp() {
		return emailEmp;
	}

	public void setEmailEmp(String emailEmp) {
		this.emailEmp = emailEmp;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public LocalDate getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(LocalDate fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public TipoIdentificacion getTipoidentificacion() {
		return tipoidentificacion;
	}

	public void setTipoidentificacion(TipoIdentificacion tipoidentificacion) {
		this.tipoidentificacion = tipoidentificacion;
	}

	public String getNumDocEmp() {
		return numDocEmp;
	}

	public void setNumDocEmp(String numDocEmp) {
		this.numDocEmp = numDocEmp;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}