package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.TipoIdentificacion;
import com.idat.currulo.web.models.repository.TipoIdentificacionRepository;

@Service
public class TipoIdentificacionServiceImpl implements TipoIdentificacionService {
	
	@Autowired
	private TipoIdentificacionRepository identificacionRepository;

	@Override
	@Transactional(readOnly = true)
	public List<TipoIdentificacion> listarIdentificaciones() {
		return identificacionRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoIdentificacion obtenerTipoIdentificacion(Integer codIdent) {
	    return identificacionRepository.findById(codIdent).orElse(null);
	}

	@Override
	@Transactional
	public TipoIdentificacion guardarTipoIdentificacion(TipoIdentificacion identificacion) {
	    return identificacionRepository.save(identificacion);
	}

	@Override
	@Transactional
	public TipoIdentificacion actualizarTipoIdentificacion(TipoIdentificacion identificacion) {
	    return identificacionRepository.save(identificacion);
	}

	@Override
	@Transactional
	public void eliminarTipoIdentificacion(Integer codIdent) {
	    identificacionRepository.deleteById(codIdent);
	}
	
}