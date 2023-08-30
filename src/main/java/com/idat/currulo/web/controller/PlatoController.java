package com.idat.currulo.web.controller;

import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idat.currulo.web.models.entity.Categoria;
import com.idat.currulo.web.models.entity.Plato;
import com.idat.currulo.web.service.CategoriaService;
import com.idat.currulo.web.service.PlatoService;
import com.idat.currulo.web.util.reportes.ProductoExporterExcel;
import com.idat.currulo.web.util.reportes.ProductoExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class PlatoController {
	
	@Autowired
	private PlatoService service;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(value = "/detallesproducto/{idPlato}")
	public String verDetallesProducto(@PathVariable(value = "idPlato") Integer idPlato, Map<String, Object> modelo, RedirectAttributes flash) {
		Plato plato = service.obtenerPlato(idPlato);
		if (plato == null) {
			flash.addAttribute("error", "¡El producto no existe en la base de datos!");
			return "redirect:/platos";
		}
		modelo.put("plato", plato);
		modelo.put("Titulo", "Detalles del Producto: " + plato.getNomPlato());
		return "detallesproducto";
	}
	
	@GetMapping(value = "/platos")
	public String gestionarplatos(Model modelo) {
		modelo.addAttribute("listplatos", service.listarPlatos());
		modelo.addAttribute("Titulo", "Productos");
		return "platos";
	}
	
	@GetMapping(value = "/platos/nuevoPlato")
	public String formularioProducto(Model modelo) {
		List<Categoria> categorias = categoriaService.listarCategorias();
		Plato plato = new Plato();
		modelo.addAttribute("plato", plato);
		modelo.addAttribute("categorias", categorias);
		return "formulario_producto";
	}
	
	/*@PostMapping(value = "/platos")
	public String guardarProducto(@ModelAttribute("plato") Plato plato, @RequestParam("file") MultipartFile imagen) {
		if (!imagen.isEmpty()) {
			try {
				Path directorioImagenes = Paths.get("src//main//resources//static/uploads");
				String rootPath = directorioImagenes.toFile().getAbsolutePath();
				byte[] bytes = imagen.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				plato.setImagen(imagen.getOriginalFilename());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		service.guardarPlato(plato);
		return "redirect:/platos";
	}*/
	
	@PostMapping(value = "/platos")
	public String guardarProducto(@ModelAttribute("plato") Plato plato) {
		service.guardarPlato(plato);
		return "redirect:/platos";
	}
	
	@GetMapping("/platos/editar/{idPlato}")
	public String formularioProductoEditar(@PathVariable Integer idPlato, Model modelo) {
		List<Categoria> categorias = categoriaService.listarCategorias();
		modelo.addAttribute("categorias", categorias);
		modelo.addAttribute("plato", service.obtenerPlato(idPlato));
		return "formulario_editarproducto";
	}
	
	@PostMapping(value = "/platos/{idPlato}")
	public String actualizarProducto(@PathVariable Integer idPlato, @ModelAttribute("plato") Plato plato, Model modelo) {
		Plato platoExistente = service.obtenerPlato(idPlato);
		platoExistente.setNomPlato(plato.getNomPlato());
		platoExistente.setDescPlato(plato.getDescPlato());
		platoExistente.setPreciou(plato.getPreciou());
		platoExistente.setStock(plato.getStock());
		platoExistente.setImagen(plato.getImagen());
		platoExistente.setCategoria(plato.getCategoria());
		boolean estadoPlato = plato.getEstado() != null && plato.getEstado();
		platoExistente.setEstado(estadoPlato);
		service.actualizarPlato(platoExistente);
		return "redirect:/platos";
	}
	
	@GetMapping(value = "/platos/{idPlato}")
	public String eliminarProducto(@PathVariable Integer idPlato) {
		service.eliminarPlato(idPlato);
		return "redirect:/platos";
	}
	
	@GetMapping(value = "/exportarProductoPDF")
	public void exportarListadodeProductosenPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Productos_" + fechaActual + ".pdf";
		response.setHeader(cabecera, valor);
		List<Plato> productos = service.listarPlatos();
		ProductoExporterPDF exporter = new ProductoExporterPDF(productos);
		exporter.exportarPDF(response);
	}
	
	@GetMapping(value = "/exportarProductoExcel")
	public void exportarListadodeProductosenExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Productos_" + fechaActual + ".xlsx";
		response.setHeader(cabecera, valor);
		List<Plato> productos = service.listarPlatos();
		ProductoExporterExcel exporter = new ProductoExporterExcel(productos);
		exporter.exportarExcel(response);
	}
	
}