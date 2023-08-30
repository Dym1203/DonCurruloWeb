package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Compra;
import com.idat.currulo.web.models.entity.DetalleCompra;
import com.idat.currulo.web.models.repository.DetalleCompraRepository;

@Service
public class DetalleCompraServiceImpl implements DetalleCompraService {
	
	@Autowired
	private DetalleCompraRepository detalleCompraRepository;

	@Override
	@Transactional
	public DetalleCompra guardarDetalleCompra(DetalleCompra detalleCompra) {
		return detalleCompraRepository.save(detalleCompra);
	}

	@Override
	@Transactional(readOnly = true)
	public DetalleCompra obtenerDetalleCompra(Integer idDetCompra) {
		return detalleCompraRepository.findById(idDetCompra).get();
	}

	@Override
	@Transactional
	public DetalleCompra actualizarDetalleCompra(DetalleCompra detalleCompra) {
		return detalleCompraRepository.save(detalleCompra);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DetalleCompra> obtenerDetallesPorCompra(Compra compra) {
		return detalleCompraRepository.findByCompra(compra);
	}
	
}