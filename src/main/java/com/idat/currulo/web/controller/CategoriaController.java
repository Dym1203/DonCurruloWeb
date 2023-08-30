package com.idat.currulo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.idat.currulo.web.models.entity.Categoria;
import com.idat.currulo.web.service.CategoriaService;

@Controller
public class CategoriaController {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping(value = "/categorias")
	public String gestionarcategorias(Model modelo) {
		modelo.addAttribute("listcategorias", service.listarCategorias());
		modelo.addAttribute("Titulo", "Categorías");
		return "categorias";
	}
	
	@GetMapping(value = "/categorias/nuevaCategoria")
	public String formularioCategoria(Model modelo) {
		Categoria categoria = new Categoria();
		modelo.addAttribute("categoria", categoria);
		return "formulario_categoria";
	}
	
	@PostMapping(value = "/categorias")
	public String guardarCategoria(@ModelAttribute("categoria") Categoria categoria) {
		service.guardarCategoria(categoria);
		return "redirect:/categorias";
	}
	
	@GetMapping("/categorias/editar/{idCategoria}")
	public String formularioCategoriaEditar(@PathVariable Integer idCategoria, Model modelo) {
		modelo.addAttribute("categoria", service.obtenerCategoria(idCategoria));
		return "formulario_editarcategoria";
	}
	
	@PostMapping(value = "/categorias/{idCategoria}")
	public String actualizarCategoria(@PathVariable Integer idCategoria, @ModelAttribute("categoria") Categoria categoria, Model modelo) {
		Categoria categoriaExistente = service.obtenerCategoria(idCategoria);
		categoriaExistente.setIdCategoria(idCategoria);
		categoriaExistente.setNomCategoria(categoria.getNomCategoria());
		boolean estadoCategoria = categoria.getEstado() != null && categoria.getEstado();
		categoriaExistente.setEstado(estadoCategoria);
		service.actualizarCategoria(categoriaExistente);
		return "redirect:/categorias";
	}
	
	@GetMapping(value = "/categorias/{idCategoria}")
	public String eliminarCategoria(@PathVariable Integer idCategoria) {
		service.eliminarCategoria(idCategoria);
		return "redirect:/categorias";
	}
	
}