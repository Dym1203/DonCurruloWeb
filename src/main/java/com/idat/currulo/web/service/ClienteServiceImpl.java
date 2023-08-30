package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Cliente;
import com.idat.currulo.web.models.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}
	
	@Override
	@Transactional
	public Cliente guardarCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente obtenerCliente(Integer idCliente) {
		return clienteRepository.findById(idCliente).get();
	}

	@Override
	@Transactional
	public Cliente actualizarCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional
	public void eliminarCliente(Integer idCliente) {
		clienteRepository.deleteById(idCliente);
	}
	
}