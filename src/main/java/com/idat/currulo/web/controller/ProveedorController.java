package com.idat.currulo.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.idat.currulo.web.models.entity.Proveedor;
import com.idat.currulo.web.models.entity.TipoIdentificacion;
import com.idat.currulo.web.service.ProveedorService;
import com.idat.currulo.web.service.TipoIdentificacionService;
import com.idat.currulo.web.util.reportes.ProveedorExporterExcel;
import com.idat.currulo.web.util.reportes.ProveedorExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class ProveedorController {
	
	@Autowired
	private ProveedorService service;
	
	@Autowired
	private TipoIdentificacionService identificacionService;
	
	@GetMapping(value = "/proveedores")
	public String gestionarproveedores(Model modelo) {
		modelo.addAttribute("listproveedores", service.listarProveedores());
		modelo.addAttribute("Titulo", "Proveedores");
		return "proveedores";
	}
	
	@GetMapping(value = "/proveedores/nuevoProveedor")
	public String formularioProveedor(Model modelo) {
		List<TipoIdentificacion> identificaciones = identificacionService.listarIdentificaciones();
		Proveedor proveedor = new Proveedor();
		modelo.addAttribute("proveedor", proveedor);
		modelo.addAttribute("identificaciones", identificaciones);
		return "formulario_proveedor";
	}
	
	@PostMapping(value = "/proveedores")
	public String guardarProveedor(@ModelAttribute("proveedor") Proveedor proveedor) {
		service.guardarProveedor(proveedor);
		return "redirect:/proveedores";
	}
	
	@GetMapping("/proveedores/editar/{idProveedor}")
	public String formularioProveedorEditar(@PathVariable Integer idProveedor, Model modelo) {
		List<TipoIdentificacion> identificaciones = identificacionService.listarIdentificaciones();
		modelo.addAttribute("identificaciones", identificaciones);
		modelo.addAttribute("proveedor", service.obtenerProveedor(idProveedor));
		return "formulario_editarproveedor";
	}
	
	@PostMapping(value = "/proveedores/{idProveedor}")
	public String actualizarProveedor(@PathVariable Integer idProveedor, @ModelAttribute("proveedor") Proveedor proveedor, Model modelo) {
		Proveedor proveedorExistente = service.obtenerProveedor(idProveedor);
		proveedorExistente.setIdProveedor(idProveedor);
		proveedorExistente.setRazonSocial(proveedor.getRazonSocial());
		proveedorExistente.setDirProv(proveedor.getDirProv());
		proveedorExistente.setEmailProv(proveedor.getEmailProv());
		proveedorExistente.setTelProv(proveedor.getTelProv());
		proveedorExistente.setTipoidentificacion(proveedor.getTipoidentificacion());
		proveedorExistente.setNumDocProv(proveedor.getNumDocProv());
		boolean estadoProveedor = proveedor.getEstado() != null && proveedor.getEstado();
		proveedorExistente.setEstado(estadoProveedor);
		service.actualizarProveedor(proveedorExistente);
		return "redirect:/proveedores";
	}
	
	@GetMapping(value = "/proveedores/{idProveedor}")
	public String eliminarProveedor(@PathVariable Integer idProveedor) {
		service.eliminarProveedor(idProveedor);
		return "redirect:/proveedores";
	}
	
	@GetMapping(value = "/exportarProveedorPDF")
	public void exportarListadodeProveedoresenPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Proveedores_" + fechaActual + ".pdf";
		response.setHeader(cabecera, valor);
		List<Proveedor> proveedores = service.listarProveedores();
		ProveedorExporterPDF exporter = new ProveedorExporterPDF(proveedores);
		exporter.exportarPDF(response);
	}
	
	@GetMapping(value = "/exportarProveedorExcel")
	public void exportarListadodeProveedoresenExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Proveedores_" + fechaActual + ".xlsx";
		response.setHeader(cabecera, valor);
		List<Proveedor> proveedores = service.listarProveedores();
		ProveedorExporterExcel exporter = new ProveedorExporterExcel(proveedores);
		exporter.exportarExcel(response);
	}
	
}