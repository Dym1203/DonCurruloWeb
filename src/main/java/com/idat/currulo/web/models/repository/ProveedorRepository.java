package com.idat.currulo.web.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
	
}