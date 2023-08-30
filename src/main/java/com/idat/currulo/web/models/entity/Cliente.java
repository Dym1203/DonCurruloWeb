package com.idat.currulo.web.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCliente;
	
	@Size(max = 40)
	@Column(nullable = false, unique = false)
	private String nomCli;
	
	@Size(max = 40)
	@Column(nullable = false, unique = false)
	private String apeCli;
	
	@Size(max = 10)
	@Column(nullable = false, unique = false)
	private String sexo;
	
	@Size(max = 60)
	@Column(nullable = false, unique = false)
	private String dirCli;
	
	@Size(max = 9)
	@Column(nullable = false, unique = true)
	private String telCli;
	
	@ManyToOne
	@JoinColumn(name = "cod_ident", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition =
				"foreign key (cod_ident) references identificaciones(cod_ident)"))
	private TipoIdentificacion tipoidentificacion;
	
	@Size(max = 11)
	@Column(nullable = false, unique = true)
	private String numDoc;
	
	@Size(max = 50)
	@Column(nullable = false, unique = true)
	private String emailCli;
	
	private String imagen;
	
	@Column(nullable = false, unique = false)
	private Boolean estado;
	
	@OneToOne
	@JoinColumn(name = "id_usuario", nullable = false, unique = true,
				foreignKey = @ForeignKey(foreignKeyDefinition = 
				"foreign key (id_usuario) references usuarios (id_usuario)"))
	private Usuario usuario;
	
	@OneToMany(mappedBy = "cliente")
	private Collection<Pedido> itemsPedidos = new ArrayList<>();
	
	@PrePersist
	public void estadoCliente() {
		estado = true;
	}
	
	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomCli() {
		return nomCli;
	}

	public void setNomCli(String nomCli) {
		this.nomCli = nomCli;
	}

	public String getApeCli() {
		return apeCli;
	}

	public void setApeCli(String apeCli) {
		this.apeCli = apeCli;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDirCli() {
		return dirCli;
	}

	public void setDirCli(String dirCli) {
		this.dirCli = dirCli;
	}

	public String getTelCli() {
		return telCli;
	}

	public void setTelCli(String telCli) {
		this.telCli = telCli;
	}

	public TipoIdentificacion getTipoidentificacion() {
		return tipoidentificacion;
	}

	public void setTipoidentificacion(TipoIdentificacion tipoidentificacion) {
		this.tipoidentificacion = tipoidentificacion;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public String getEmailCli() {
		return emailCli;
	}

	public void setEmailCli(String emailCli) {
		this.emailCli = emailCli;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Collection<Pedido> getItemsPedidos() {
		return itemsPedidos;
	}

	public void setItemsPedidos(Collection<Pedido> itemsPedidos) {
		this.itemsPedidos = itemsPedidos;
	}
		
}