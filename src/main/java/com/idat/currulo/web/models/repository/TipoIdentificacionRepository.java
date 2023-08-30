package com.idat.currulo.web.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.TipoIdentificacion;

public interface TipoIdentificacionRepository extends JpaRepository<TipoIdentificacion, Integer> {
	
}