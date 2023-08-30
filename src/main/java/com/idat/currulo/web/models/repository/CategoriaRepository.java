package com.idat.currulo.web.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	
}