package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Proveedor;

public interface ProveedorService {
	
	public List<Proveedor> listarProveedores();
	
	public Proveedor guardarProveedor(Proveedor proveedor);
	
	public Proveedor obtenerProveedor(Integer idProveedor);
	
	public Proveedor actualizarProveedor(Proveedor proveedor);
	
	public void eliminarProveedor(Integer idProveedor);
	
}