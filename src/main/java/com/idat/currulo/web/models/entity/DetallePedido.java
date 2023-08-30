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
@Table(name = "detalle_pedido")
public class DetallePedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetpedido;
	
	@ManyToOne
	@JoinColumn(name = "id_pedido", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key (id_pedido) references pedidos (id_pedido)"))
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name = "id_plato", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key (id_plato) references platos (id_plato)"))
	private Plato plato;
	
	@Column(nullable = false)
	private Integer cantidad;
	
	@Column(nullable = false)
	private Double importe;

	public Integer getIdDetpedido() {
		return idDetpedido;
	}

	public void setIdDetpedido(Integer idDetpedido) {
		this.idDetpedido = idDetpedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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