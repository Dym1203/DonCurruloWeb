package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Plato;
import com.idat.currulo.web.models.repository.PlatoRepository;

@Service
public class PlatoServiceImpl implements PlatoService {
	
	@Autowired
	private PlatoRepository platoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Plato> listarPlatos() {
		return platoRepository.findAll();
	}

	@Override
	@Transactional
	public Plato guardarPlato(Plato plato) {
		return platoRepository.save(plato);
	}

	@Override
	@Transactional(readOnly = true)
	public Plato obtenerPlato(Integer idPlato) {
		return platoRepository.findById(idPlato).get();
	}

	@Override
	@Transactional
	public Plato actualizarPlato(Plato plato) {
		return platoRepository.save(plato);
	}

	@Override
	@Transactional
	public void eliminarPlato(Integer idPlato) {
		platoRepository.deleteById(idPlato);
	}
	
}