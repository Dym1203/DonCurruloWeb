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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "proveedores")
public class Proveedor implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProveedor;
	
	@Size(max = 30)
	@Column(nullable = false, unique = true)
	private String razonSocial;
	
	@Size(max = 60)
	@Column(nullable = false, unique = false)
	private String dirProv;
	
	@Size(max = 50)
	@Column(nullable = false, unique = true)
	private String emailProv;
	
	@Size(max = 9)
	@Column(nullable = false, unique = true)
	private String telProv;
	
	@ManyToOne
	@JoinColumn(name = "cod_ident", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition =
				"foreign key (cod_ident) references identificaciones(cod_ident)"))
	private TipoIdentificacion tipoidentificacion;
	
	@Size(max = 11)
	@Column(nullable = false)
	private String numDocProv;
	
	@Column(nullable = false)
	private Boolean estado;
	
	@OneToMany(mappedBy = "proveedor")
	private Collection<Compra> itemsCompras = new ArrayList<>();
	
	@PrePersist
	public void estadoProveedor() {
		estado = true;
	}
	
	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDirProv() {
		return dirProv;
	}

	public void setDirProv(String dirProv) {
		this.dirProv = dirProv;
	}

	public String getEmailProv() {
		return emailProv;
	}

	public void setEmailProv(String emailProv) {
		this.emailProv = emailProv;
	}

	public String getTelProv() {
		return telProv;
	}

	public void setTelProv(String telProv) {
		this.telProv = telProv;
	}

	public TipoIdentificacion getTipoidentificacion() {
		return tipoidentificacion;
	}

	public void setTipoidentificacion(TipoIdentificacion tipoidentificacion) {
		this.tipoidentificacion = tipoidentificacion;
	}

	public String getNumDocProv() {
		return numDocProv;
	}

	public void setNumDocProv(String numDocProv) {
		this.numDocProv = numDocProv;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Collection<Compra> getItemsCompras() {
		return itemsCompras;
	}

	public void setItemsCompras(Collection<Compra> itemsCompras) {
		this.itemsCompras = itemsCompras;
	}
	
}