package com.idat.currulo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.idat.currulo.web.models.entity.TipoIdentificacion;
import com.idat.currulo.web.service.TipoIdentificacionService;

@Controller
public class TipoIdentificacionController {
	
	@Autowired
	private TipoIdentificacionService service;
	
	@GetMapping(value = "/identificaciones")
	public String gestionaridentificaciones(Model modelo) {
		modelo.addAttribute("listidentificaciones", service.listarIdentificaciones());
		modelo.addAttribute("Titulo", "Identificaciones");
		return "identificaciones";
	}
	
	@GetMapping(value = "/identificaciones/nuevoTipoIdentificacion")
	public String formularioTipoIdentificacion(Model modelo) {
	    TipoIdentificacion identificacion = new TipoIdentificacion();
	    modelo.addAttribute("identificacion", identificacion);
	    return "formulario_tipoidentificacion";
	}
	
	@PostMapping(value = "/identificaciones")
	public String guardarTipoIdentificacion(@ModelAttribute("identificacion") TipoIdentificacion identificacion) {
	    service.guardarTipoIdentificacion(identificacion);
	    return "redirect:/identificaciones";
	}
	
	@GetMapping("/identificaciones/editar/{codIdent}")
	public String formularioTipoIdentificacionEditar(@PathVariable Integer codIdent, Model modelo) {
	    modelo.addAttribute("identificacion", service.obtenerTipoIdentificacion(codIdent));
	    return "formulario_editartipoidentificacion";
	}
	
	@PostMapping(value = "/identificaciones/{codIdent}")
	public String actualizarTipoIdentificacion(@PathVariable Integer codIdent, @ModelAttribute("identificacion") TipoIdentificacion identificacion, Model modelo) {
	    TipoIdentificacion identificacionExistente = service.obtenerTipoIdentificacion(codIdent);
	    identificacionExistente.setCodIdent(codIdent);
	    identificacionExistente.setTipoIdent(identificacion.getTipoIdent());
	    boolean estadoIdentificacion = identificacion.getEstado() != null && identificacion.getEstado();
	    identificacionExistente.setEstado(estadoIdentificacion);
	    service.actualizarTipoIdentificacion(identificacionExistente);
	    return "redirect:/identificaciones";
	}
	
	@GetMapping(value = "/identificaciones/{codIdent}")
	public String eliminarTipoIdentificacion(@PathVariable Integer codIdent) {
	    service.eliminarTipoIdentificacion(codIdent);
	    return "redirect:/identificaciones";
	}
	
}