package com.idat.currulo.web.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.Plato;

public interface PlatoRepository extends JpaRepository<Plato, Integer> {
	
}