package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Compra;
import com.idat.currulo.web.models.entity.DetalleCompra;

public interface DetalleCompraService {
	
	public DetalleCompra guardarDetalleCompra(DetalleCompra detalleCompra);
	
	public DetalleCompra obtenerDetalleCompra(Integer idDetCompra);
	
	public DetalleCompra actualizarDetalleCompra(DetalleCompra detalleCompra);
	
	public List<DetalleCompra> obtenerDetallesPorCompra(Compra compra);
	
}