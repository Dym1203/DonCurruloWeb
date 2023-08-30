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
@Table(name = "platos")
public class Plato implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPlato;
	
	@Size(max = 30)
	@Column(nullable = false, unique = true)
	private String nomPlato;
	
	@Column(nullable = false, unique = false)
	private String descPlato;
	
	@Column(nullable = false)
	private Double preciou;
	
	@Column(nullable = false)
	private Integer stock;
	
	private String imagen;
	
	@Column(nullable = false)
	private Boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition = 
				"foreign key (id_categoria) references categorias (id_categoria)"))
	private Categoria categoria;
	
	@OneToMany(mappedBy = "plato")
	private Collection<DetallePedido> itemsDetalle = new ArrayList<>();
	
	@OneToMany(mappedBy = "plato")
	private Collection<DetalleCompra> itemsDetalleCompras = new ArrayList<>();
	
	@PrePersist
	public void estadoPlato() {
		estado = true;
	}
	
	public Integer getIdPlato() {
		return idPlato;
	}

	public void setIdPlato(Integer idPlato) {
		this.idPlato = idPlato;
	}

	public String getNomPlato() {
		return nomPlato;
	}

	public void setNomPlato(String nomPlato) {
		this.nomPlato = nomPlato;
	}

	public String getDescPlato() {
		return descPlato;
	}

	public void setDescPlato(String descPlato) {
		this.descPlato = descPlato;
	}

	public Double getPreciou() {
		return preciou;
	}

	public void setPreciou(Double preciou) {
		this.preciou = preciou;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Collection<DetallePedido> getItemsDetalle() {
		return itemsDetalle;
	}

	public void setItemsDetalle(Collection<DetallePedido> itemsDetalle) {
		this.itemsDetalle = itemsDetalle;
	}

	public Collection<DetalleCompra> getItemsDetalleCompras() {
		return itemsDetalleCompras;
	}

	public void setItemsDetalleCompras(Collection<DetalleCompra> itemsDetalleCompras) {
		this.itemsDetalleCompras = itemsDetalleCompras;
	}
	
}