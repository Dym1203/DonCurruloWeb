package com.idat.currulo.web.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
	
	Rol findByNomRol(String nomRol);
	
}