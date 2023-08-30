package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Plato;

public interface PlatoService {

	public List<Plato> listarPlatos();
	
	public Plato guardarPlato(Plato plato);
	
	public Plato obtenerPlato(Integer idPlato);
	
	public Plato actualizarPlato(Plato plato);
	
	public void eliminarPlato(Integer idPlato);
	
}