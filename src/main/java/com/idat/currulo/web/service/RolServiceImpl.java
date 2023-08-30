package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Rol;
import com.idat.currulo.web.models.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {
	
	@Autowired
	private RolRepository rolRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Rol> listarRoles() {
		return rolRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Rol obtenerRolPorId(Integer idRol) {
		return rolRepository.findById(idRol).get();
	}

	@Override
	@Transactional
	public Rol guardarRol(Rol rol) {
		return rolRepository.save(rol);
	}

	@Override
	@Transactional
	public Rol actualizarRol(Rol rol) {
		return rolRepository.save(rol);
	}

	@Override
	@Transactional
	public void eliminarRol(Integer idRol) {
		rolRepository.deleteById(idRol);
	}
	
}