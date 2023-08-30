package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Cliente;

public interface ClienteService {
	
	public List<Cliente> listarClientes();
	
	public Cliente guardarCliente(Cliente cliente);
	
	public Cliente obtenerCliente(Integer idCliente);

	public Cliente actualizarCliente(Cliente cliente);
	
	public void eliminarCliente(Integer idCliente);
	
}