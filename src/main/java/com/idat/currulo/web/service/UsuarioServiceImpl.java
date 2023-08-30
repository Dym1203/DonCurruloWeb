package com.idat.currulo.web.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.dto.UsuarioRegistroDTO;
import com.idat.currulo.web.models.entity.Rol;
import com.idat.currulo.web.models.entity.Usuario;
import com.idat.currulo.web.models.repository.RolRepository;
import com.idat.currulo.web.models.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;

	@Override
	public Usuario guardar(UsuarioRegistroDTO registroDTO) {
		Rol empleadoRol = rolRepository.findByNomRol("Vendedora");
		if (empleadoRol == null) {
			throw new RuntimeException("¡El rol 'Vendedora' no existe en la base de datos!");
		}
		Usuario usuario = new Usuario(registroDTO.getNomUsuario(), passwordEncoder.encode(registroDTO.getPassword()),
						registroDTO.getEstado(), Arrays.asList(empleadoRol));
		return usuarioRepository.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findBynomUsuario(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("¡Usuario o password inválidos!");
		}
		return new User(usuario.getNomUsuario(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNomRol())).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario obtenerUsuarioPorId(Integer idUsuario) {
		return usuarioRepository.findById(idUsuario).get();
	}
	
	@Override
	@Transactional
	public Usuario guardarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@Override
	@Transactional
	public Usuario actualizarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	@Transactional
	public void eliminarUsuario(Integer idUsuario) {
		usuarioRepository.deleteById(idUsuario);
	}
	
}