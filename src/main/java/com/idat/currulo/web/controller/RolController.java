package com.idat.currulo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.idat.currulo.web.models.entity.Rol;
import com.idat.currulo.web.service.RolService;

@Controller
public class RolController {
	
	@Autowired
	private RolService service;
	
	@GetMapping(value = "/roles")
	public String gestionaroles(Model modelo) {
		modelo.addAttribute("listroles", service.listarRoles());
		modelo.addAttribute("Titulo", "Roles");
		return "roles";
	}
	
	@GetMapping(value = "/roles/nuevoRol")
    public String formularioRol(Model model) {
        Rol rol = new Rol();
        model.addAttribute("rol", rol);
        return "formulario_rol";
    }
	
	@PostMapping(value = "/roles")
    public String guardarRol(@ModelAttribute("rol") Rol rol) {
        service.guardarRol(rol);
        return "redirect:/roles";
    }
	
	@GetMapping("/roles/editar/{idRol}")
    public String formularioRolEditar(@PathVariable("idRol") Integer idRol, Model model) {
		model.addAttribute("rol", service.obtenerRolPorId(idRol));
		return "formulario_editarrol";
    }

    @PostMapping(value = "/roles/{idRol}")
    public String actualizarRol(@PathVariable Integer idRol, @ModelAttribute("rol") Rol rol, Model model) {
        Rol rolExistente = service.obtenerRolPorId(idRol);
        rolExistente.setIdRol(idRol);
        rolExistente.setNomRol(rol.getNomRol());
        boolean estadoRol = rol.getEstado() != null && rol.getEstado();
        rolExistente.setEstado(estadoRol);
    	service.actualizarRol(rolExistente);
        return "redirect:/roles";
    }

    @GetMapping(value = "/roles/{idRol}")
    public String eliminarRol(@PathVariable("idRol") Integer idRol) {
        service.eliminarRol(idRol);
        return "redirect:/roles";
    }
	
}