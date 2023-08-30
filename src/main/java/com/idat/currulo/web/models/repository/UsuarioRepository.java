package com.idat.currulo.web.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.currulo.web.models.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	public Usuario findBynomUsuario(String nomUsuario);
	
}