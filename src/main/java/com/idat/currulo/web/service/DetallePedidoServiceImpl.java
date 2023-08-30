package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.DetallePedido;
import com.idat.currulo.web.models.entity.Pedido;
import com.idat.currulo.web.models.repository.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {
	
	@Autowired
	private DetallePedidoRepository detallePedidoRepository;

	@Override
	@Transactional
	public DetallePedido guardarDetalle(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}

	@Override
	@Transactional(readOnly = true)
	public DetallePedido obtenerDetalle(Integer idDetpedido) {
		return detallePedidoRepository.findById(idDetpedido).get();
	}

	@Override
	@Transactional
	public DetallePedido actualizarDetalle(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DetallePedido> obtenerDetallesPorPedido(Pedido pedido) {
		return detallePedidoRepository.findByPedido(pedido);
	}
	
}