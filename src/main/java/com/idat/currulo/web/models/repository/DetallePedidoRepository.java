package com.idat.currulo.web.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.DetallePedido;
import com.idat.currulo.web.models.entity.Pedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
	
	List<DetallePedido> findByPedido(Pedido pedido);
	
}