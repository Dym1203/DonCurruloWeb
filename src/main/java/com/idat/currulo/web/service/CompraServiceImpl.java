package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Compra;
import com.idat.currulo.web.models.repository.CompraRepository;

@Service
public class CompraServiceImpl implements CompraService {
	
	@Autowired
	private CompraRepository compraRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Compra> listarCompras() {
		return compraRepository.findAll();
	}

	@Override
	@Transactional
	public Compra guardarCompra(Compra compra) {
		return compraRepository.save(compra);
	}

	@Override
	@Transactional(readOnly = true)
	public Compra obtenerCompra(Integer idCompra) {
		return compraRepository.findById(idCompra).get();
	}

	@Override
	@Transactional
	public Compra actualizarCompra(Compra compra) {
		return compraRepository.save(compra);
	}

	@Override
	@Transactional
	public void eliminarCompra(Integer idCompra) {
		compraRepository.deleteById(idCompra);
	}
	
}