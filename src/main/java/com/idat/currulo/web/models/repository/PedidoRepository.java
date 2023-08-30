package com.idat.currulo.web.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
}