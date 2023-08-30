package com.idat.currulo.web.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
	
}