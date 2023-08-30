package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Pedido;

public interface PedidoService {
	
	public List<Pedido> listarPedidos();
	
	public Pedido guardarPedido(Pedido pedido);
	
	public Pedido obtenerPedido(Integer idPedido);
	
	public Pedido actualizarPedido(Pedido pedido);
	
	public void eliminarPedido(Integer idPedido);
	
}