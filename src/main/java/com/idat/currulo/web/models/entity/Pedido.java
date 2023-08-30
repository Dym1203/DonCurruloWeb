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
@Table(name = "pedidos")
public class Pedido implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPedido;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = ISO.DATE_TIME)
	@Column(nullable = false)
	private LocalDateTime fechaCompra;
	
	@Column(nullable = false)
	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false,
				foreignKey = @ForeignKey(foreignKeyDefinition = 
				"foreign key (id_cliente) references clientes (id_cliente)"))
	private Cliente cliente;
	
	@Size(max = 30)
	@Column(nullable = false, unique = false)
	private String metpago;
	
	@Column(nullable = false, unique = false)
	private String comentarios;
	
	@Column(nullable = false, unique = false)
	private Boolean estado;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
	private Collection<DetallePedido> itemsDetalle = new ArrayList<>();
	
	@PrePersist
	public void estadoPedido() {
		estado = true;
	}
	
	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getMetpago() {
		return metpago;
	}

	public void setMetpago(String metpago) {
		this.metpago = metpago;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Collection<DetallePedido> getItemsDetalle() {
		return itemsDetalle;
	}

	public void setItemsDetalle(Collection<DetallePedido> itemsDetalle) {
		this.itemsDetalle = itemsDetalle;
	}
	
}