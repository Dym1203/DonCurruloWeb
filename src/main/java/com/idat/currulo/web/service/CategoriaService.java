package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Categoria;

public interface CategoriaService {
	
	public List<Categoria> listarCategorias();
	
	public Categoria guardarCategoria(Categoria categoria);
	
	public Categoria obtenerCategoria(Integer idCategoria);
	
	public Categoria actualizarCategoria(Categoria categoria);
	
	public void eliminarCategoria(Integer idCategoria);
	
}