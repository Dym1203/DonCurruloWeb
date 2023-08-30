package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Pedido;
import com.idat.currulo.web.models.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Pedido> listarPedidos() {
		return pedidoRepository.findAll();
	}

	@Override
	@Transactional
	public Pedido guardarPedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido obtenerPedido(Integer idPedido) {
		return pedidoRepository.findById(idPedido).get();
	}

	@Override
	@Transactional
	public Pedido actualizarPedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@Override
	@Transactional
	public void eliminarPedido(Integer idPedido) {
		pedidoRepository.deleteById(idPedido);
	}
	
}