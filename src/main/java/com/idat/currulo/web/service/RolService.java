package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Rol;

public interface RolService {

	public List<Rol> listarRoles();
	
	public Rol obtenerRolPorId(Integer idRol);
	
    public Rol guardarRol(Rol rol);
    
    public Rol actualizarRol(Rol rol);
    
    public void eliminarRol(Integer idRol);
}