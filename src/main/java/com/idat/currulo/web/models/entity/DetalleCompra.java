package com.idat.currulo.web.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_compra")
public class DetalleCompra implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetCompra;
	
	@ManyToOne
	@JoinColumn(name = "id_compra", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key (id_compra) references compras (id_compra)"))
	private Compra compra;
	
	@ManyToOne
	@JoinColumn(name = "id_plato", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key (id_plato) references platos (id_plato)"))
	private Plato plato;
	
	@Column(nullable = false)
	private Integer cantidad;
	
	@Column(nullable = false)
	private Double importe;

	public Integer getIdDetCompra() {
		return idDetCompra;
	}

	public void setIdDetCompra(Integer idDetCompra) {
		this.idDetCompra = idDetCompra;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

}