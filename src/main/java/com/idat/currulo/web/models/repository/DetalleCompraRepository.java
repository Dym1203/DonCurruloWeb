package com.idat.currulo.web.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.Compra;
import com.idat.currulo.web.models.entity.DetalleCompra;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Integer> {
	
	List<DetalleCompra> findByCompra(Compra compra);
	
}