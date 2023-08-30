package com.idat.currulo.web.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idat.currulo.web.models.entity.Cliente;
import com.idat.currulo.web.models.entity.Empleado;
import com.idat.currulo.web.models.entity.Rol;
import com.idat.currulo.web.models.entity.Usuario;
import com.idat.currulo.web.service.CompraService;
import com.idat.currulo.web.service.PedidoService;
import com.idat.currulo.web.service.RolService;
import com.idat.currulo.web.service.UsuarioService;

@Controller
public class HomeController {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private CompraService compraService;
	
	/*Sección del Admin Dashboard*/
	@GetMapping(value = {"/", "/index"})
	public String index (Model modelo) {
		modelo.addAttribute("listpedidos", pedidoService.listarPedidos());
		modelo.addAttribute("listcompras", compraService.listarCompras());
		modelo.addAttribute("Titulo", "Sistema Don Currulo");
		return "index";
	}
	
	/*Sección del Recuperar Contraseña - Negocio*/
	@RequestMapping(value = "/recoverpassword")
	public String recoverpassword (Model modelo) {
		modelo.addAttribute("Titulo", "Recuperar Contraseña");
		return "recoverpassword";
	}
	
	@RequestMapping(value = "/insertcode")
	public String insertcode (Model modelo) {
		modelo.addAttribute("Titulo", "Ingresar Código");
		return "insertcode";
	}
	
	@RequestMapping(value = "/newpassword")
	public String newpassword (Model modelo) {
		modelo.addAttribute("Titulo", "Nueva Contraseña");
		return "newpassword";
	}
	
	/*Sección de los Usuarios*/
	@GetMapping(value = "/detallesusuario/{idUsuario}")
	public String verDetallesUsuario(@PathVariable(value = "idUsuario") Integer idUsuario,
			Map<String, Object> modelo, RedirectAttributes flash) {
		Usuario usuario = service.obtenerUsuarioPorId(idUsuario);
		if (usuario == null) {
			flash.addAttribute("error", "¡El usuario no existe en la base de datos!");
			return "redirect:/usuarios";
		}
		Cliente cliente = usuario.getCliente();
		Empleado empleado = usuario.getEmpleado();
		modelo.put("usuario", usuario);
		modelo.put("cliente", cliente);
		modelo.put("empleado", empleado);
		modelo.put("Titulo", "Detalles del Usuario: " + usuario.getNomUsuario());
		return "detallesusuario";
	}
	
	@GetMapping(value = "/usuarios")
	public String gestionarusuarios (Model modelo) {
		modelo.addAttribute("listusers", service.listarUsuarios());
		modelo.addAttribute("Titulo", "Usuarios");
		return "usuarios";
	}
	
	@GetMapping(value = "/usuarios/nuevoUsuario")
	public String formularioUsuario(Model modelo) {
		List<Rol> roles = rolService.listarRoles();
		Usuario usuario = new Usuario();
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("roles", roles);
		return "formulario_usuario";
	}
	
	@PostMapping(value = "/usuarios")
	public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
		String passwordEncriptado = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setPassword(passwordEncriptado);
		service.guardarUsuario(usuario);
		return "redirect:/usuarios";
	}
	
	@GetMapping("/usuarios/editar/{idUsuario}")
	public String formularioUsuarioEditar(@PathVariable("idUsuario") Integer idUsuario, Model model) {
		List<Rol> roles = rolService.listarRoles();
		model.addAttribute("roles", roles);
		model.addAttribute("usuario", service.obtenerUsuarioPorId(idUsuario));
		return "formulario_editarusuario";
	}
	
	@PostMapping(value = "/usuarios/{idUsuario}")
	public String actualizarUsuario(@PathVariable Integer idUsuario, @ModelAttribute("usuario") Usuario usuario,
			@RequestParam(value = "roles", required = false) List<Integer> rolesIds, Model model) {
		Usuario usuarioExistente = service.obtenerUsuarioPorId(idUsuario);
		usuarioExistente.setIdUsuario(idUsuario);
		usuarioExistente.setNomUsuario(usuario.getNomUsuario());
		boolean estadoUsuario = usuario.getEstado() != null && usuario.getEstado();
		usuarioExistente.setEstado(estadoUsuario);
		if (rolesIds != null) {
	        List<Rol> rolesSeleccionados = rolesIds.stream()
	                .map(rolId -> rolService.obtenerRolPorId(rolId))
	                .collect(Collectors.toList());
	        usuarioExistente.setRoles(rolesSeleccionados);
	    }
		service.actualizarUsuario(usuarioExistente);
		return "redirect:/usuarios";
	}
	
	@GetMapping(value = "/usuarios/{idUsuario}")
	public String eliminarUsuario(@PathVariable Integer idUsuario) {
		service.eliminarUsuario(idUsuario);
		return "redirect:/usuarios";
	}
	
}