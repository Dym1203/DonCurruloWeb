package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Compra;

public interface CompraService {
	
	public List<Compra> listarCompras();
	
	public Compra guardarCompra(Compra compra);
	
	public Compra obtenerCompra(Integer idCompra);
	
	public Compra actualizarCompra(Compra compra);
	
	public void eliminarCompra(Integer idCompra);
	
}