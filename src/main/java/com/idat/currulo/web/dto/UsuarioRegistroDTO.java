package com.idat.currulo.web.dto;

public class UsuarioRegistroDTO {
	
	private Integer idUsuario;
	
	private String nomUsuario;
	
	private String password;
	
	private Boolean estado;
	
	public UsuarioRegistroDTO(Integer idUsuario, String nomUsuario, String password, Boolean estado) {
		this.idUsuario = idUsuario;
		this.nomUsuario = nomUsuario;
		this.password = password;
		this.estado = estado;
	}
	
	public UsuarioRegistroDTO(String nomUsuario, String password, Boolean estado) {
		this.nomUsuario = nomUsuario;
		this.password = password;
		this.estado = estado;
	}

	public UsuarioRegistroDTO(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	
	public UsuarioRegistroDTO() {
		
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
	
}