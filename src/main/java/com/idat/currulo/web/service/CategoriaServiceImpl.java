package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Categoria;
import com.idat.currulo.web.models.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categoria> listarCategorias() {
		return categoriaRepository.findAll();
	}

	@Override
	@Transactional
	public Categoria guardarCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria obtenerCategoria(Integer idCategoria) {
		return categoriaRepository.findById(idCategoria).get();
	}

	@Override
	@Transactional
	public Categoria actualizarCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	@Transactional
	public void eliminarCategoria(Integer idCategoria) {
		categoriaRepository.deleteById(idCategoria);
	}
	
}