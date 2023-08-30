package com.idat.currulo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.idat.currulo.web.dto.UsuarioRegistroDTO;
import com.idat.currulo.web.service.UsuarioService;

@Controller
@RequestMapping("/register")
public class RegistroUsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@ModelAttribute("usuario")
	public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
		return new UsuarioRegistroDTO();
	}
	
	@GetMapping
	public String mostrarFormularioDeRegistro(@RequestParam(name = "exito", required = false) String exito, Model modelo) {
		if (exito != null) {
			modelo.addAttribute("successMessage", "¡Se ha registrado exitosamente a la aplicación!");
		}
		return "register";
	}
	
	@PostMapping
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO) {
		usuarioService.guardar(registroDTO);
		return "redirect:/register?exito";
	}
	
}