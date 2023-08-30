package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Proveedor;
import com.idat.currulo.web.models.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService {
	
	@Autowired
	private ProveedorRepository proveedorRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> listarProveedores() {
		return proveedorRepository.findAll();
	}

	@Override
	@Transactional
	public Proveedor guardarProveedor(Proveedor proveedor) {
		return proveedorRepository.save(proveedor);
	}

	@Override
	@Transactional(readOnly = true)
	public Proveedor obtenerProveedor(Integer idProveedor) {
		return proveedorRepository.findById(idProveedor).get();
	}

	@Override
	@Transactional
	public Proveedor actualizarProveedor(Proveedor proveedor) {
		return proveedorRepository.save(proveedor);
	}

	@Override
	@Transactional
	public void eliminarProveedor(Integer idProveedor) {
		proveedorRepository.deleteById(idProveedor);
	}
	
}