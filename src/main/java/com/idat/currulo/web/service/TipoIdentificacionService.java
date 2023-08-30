package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.TipoIdentificacion;

public interface TipoIdentificacionService {
	
	public List<TipoIdentificacion> listarIdentificaciones();
	
	public TipoIdentificacion guardarTipoIdentificacion(TipoIdentificacion identificacion);
	
	public TipoIdentificacion obtenerTipoIdentificacion(Integer codIdent);
	
	public TipoIdentificacion actualizarTipoIdentificacion(TipoIdentificacion identificacion);
	
	public void eliminarTipoIdentificacion(Integer codIdent);
	
}