package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.idat.currulo.web.dto.UsuarioRegistroDTO;
import com.idat.currulo.web.models.entity.Usuario;

public interface UsuarioService extends UserDetailsService {
	
	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	public List<Usuario> listarUsuarios();
	
	public Usuario guardarUsuario(Usuario usuario);
	
	public Usuario obtenerUsuarioPorId(Integer idUsuario);
	
	public Usuario actualizarUsuario(Usuario usuario);
	
	public void eliminarUsuario(Integer idUsuario);
		
}