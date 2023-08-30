package com.idat.currulo.web.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "compras")
public class Compra implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCompra;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = ISO.DATE_TIME)
	@Column(nullable = false)
	private LocalDateTime fecha;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = false)
	private Double total;
	
	@Size(max = 30)
	@Column(nullable = false)
	private String metpago;
	
	@ManyToOne
	@JoinColumn(name = "id_proveedor", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition = 
				"foreign key (id_proveedor) references proveedores (id_proveedor)"))
	private Proveedor proveedor;
	
	@Column(nullable = false, unique = false)
	private Boolean estado;
	
	@OneToMany(mappedBy = "compra", cascade = CascadeType.REMOVE)
	private Collection<DetalleCompra> itemsDetalleCompras = new ArrayList<>();
	
	@PrePersist
	public void estadoCompra() {
		estado = true;
	}

	public Integer getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMetpago() {
		return metpago;
	}

	public void setMetpago(String metpago) {
		this.metpago = metpago;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Collection<DetalleCompra> getItemsDetalleCompras() {
		return itemsDetalleCompras;
	}

	public void setItemsDetalleCompras(Collection<DetalleCompra> itemsDetalleCompras) {
		this.itemsDetalleCompras = itemsDetalleCompras;
	}
	
}