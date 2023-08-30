package com.idat.currulo.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idat.currulo.web.models.entity.Compra;
import com.idat.currulo.web.models.entity.DetalleCompra;
import com.idat.currulo.web.models.entity.Plato;
import com.idat.currulo.web.models.entity.Proveedor;
import com.idat.currulo.web.service.CompraService;
import com.idat.currulo.web.service.DetalleCompraService;
import com.idat.currulo.web.service.PlatoService;
import com.idat.currulo.web.service.ProveedorService;
import com.idat.currulo.web.util.reportes.CompraExporterExcel;
import com.idat.currulo.web.util.reportes.CompraExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class CompraController {
	
	@Autowired
	private CompraService service;
	
	@Autowired
	private DetalleCompraService detalleCompraService;
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private PlatoService productoService;
	
	@GetMapping(value = "/detallescompra/{idCompra}")
	public String verDetallesCompra(@PathVariable(value = "idCompra") Integer idCompra, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Compra compra = service.obtenerCompra(idCompra);
		if (compra == null) {
			flash.addAttribute("error", "¡La compra no existe en la base de datos!");
			return "redirect:/compras";
		}
		List<DetalleCompra> detallesCompra = detalleCompraService.obtenerDetallesPorCompra(compra);
		modelo.put("compra", compra);
		modelo.put("detallesCompra", detallesCompra);
		modelo.put("Titulo", "Información de la Compra: CP000" + compra.getIdCompra().toString());
		return "detallescompra";
	}
	
	@GetMapping(value = "/compras")
	public String gestionarcompras(Model modelo) {
		modelo.addAttribute("listcompras", service.listarCompras());
		modelo.addAttribute("Titulo", "Compras");
		return "compras";
	}
	
	@GetMapping(value = "/compras/nuevaCompra")
	public String formularioCompra(Model modelo) {
		List<Proveedor> proveedores = proveedorService.listarProveedores();
		List<Plato> productos = productoService.listarPlatos();
		Compra compra = new Compra();
		compra.setFecha(LocalDateTime.now());
		compra.setTotal(0.00);
		compra.setDescripcion("N/A");
		modelo.addAttribute("compra", compra);
		modelo.addAttribute("proveedores", proveedores);
		modelo.addAttribute("productos", productos);
		return "formulario_compra";
	}
	
	@PostMapping(value = "/compras")
	public String guardarCompra(@ModelAttribute("compra") Compra compra,
								@RequestParam("idPlato") Integer[] idPlatos,
								@RequestParam("cantidad") Integer[] cantidades,
								@RequestParam("importe") Double[] importes) {
		service.guardarCompra(compra);
		for (int i = 0; i < idPlatos.length; i++) {
			DetalleCompra detalle = new DetalleCompra();
			detalle.setCompra(compra);
			Plato producto = productoService.obtenerPlato(idPlatos[i]);
			detalle.setPlato(producto);
			detalle.setCantidad(cantidades[i]);
			detalle.setImporte(importes[i]);
			detalleCompraService.guardarDetalleCompra(detalle);
		}
		return "redirect:/compras";
	}
	
	@GetMapping("/compras/editar/{idCompra}")
	public String formularioCompraEditar(@PathVariable Integer idCompra, Model modelo) {
		List<Proveedor> proveedores = proveedorService.listarProveedores();
		List<Plato> productos = productoService.listarPlatos();
		modelo.addAttribute("proveedores", proveedores);
		modelo.addAttribute("productos", productos);
		modelo.addAttribute("compra", service.obtenerCompra(idCompra));
		return "formulario_editarcompra";
	}
	
	@PostMapping(value = "/compras/{idCompra}")
	public String actualizarCompra(@PathVariable Integer idCompra, @ModelAttribute("compra") Compra compra) {
		Compra compraExistente = service.obtenerCompra(idCompra);
		compraExistente.setIdCompra(idCompra);
		compraExistente.setFecha(compra.getFecha());
		compraExistente.setDescripcion(compra.getDescripcion());
		compraExistente.setTotal(compra.getTotal());
		compraExistente.setMetpago(compra.getMetpago());
		compraExistente.setProveedor(compra.getProveedor());
		boolean estadoCompra = compra.getEstado() != null && compra.getEstado();
		compraExistente.setEstado(estadoCompra);
		service.actualizarCompra(compraExistente);
		for (DetalleCompra detalle : compra.getItemsDetalleCompras()) {
			DetalleCompra detalleExistente = detalleCompraService.obtenerDetalleCompra(detalle.getIdDetCompra());
			detalleExistente.setCantidad(detalle.getCantidad());
			detalleExistente.setImporte(detalle.getImporte());
			Plato producto = productoService.obtenerPlato(detalle.getPlato().getIdPlato());
			detalleExistente.setPlato(producto);
			detalleCompraService.actualizarDetalleCompra(detalleExistente);
		}
		return "redirect:/compras";
	}
	
	@GetMapping(value = "/compras/{idCompra}")
	public String eliminarCompra(@PathVariable Integer idCompra) {
		service.eliminarCompra(idCompra);
		return "redirect:/compras";
	}
	
	@GetMapping(value = "/exportarCompraPDF")
	public void exportarListadodeComprasenPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Compras_" + fechaActual + ".pdf";
		response.setHeader(cabecera, valor);
		List<Compra> compras = service.listarCompras();
		CompraExporterPDF exporter = new CompraExporterPDF(compras);
		exporter.exportarPDF(response);
	}
	
	@GetMapping(value = "/exportarCompraExcel")
	public void exportarListadodeComprasenExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Compras_" + fechaActual + ".xlsx";
		response.setHeader(cabecera, valor);
		List<Compra> compras = service.listarCompras();
		CompraExporterExcel exporter = new CompraExporterExcel(compras);
		exporter.exportarExcel(response);
	}
	
}