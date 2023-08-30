package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.DetallePedido;
import com.idat.currulo.web.models.entity.Pedido;

public interface DetallePedidoService {
	
	public DetallePedido guardarDetalle(DetallePedido detallePedido);
	
	public DetallePedido obtenerDetalle(Integer idDetpedido);
	
	public DetallePedido actualizarDetalle(DetallePedido detallePedido);
	
	public List<DetallePedido> obtenerDetallesPorPedido(Pedido pedido);
	
}