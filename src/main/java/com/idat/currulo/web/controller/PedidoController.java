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

import com.idat.currulo.web.models.entity.Cliente;
import com.idat.currulo.web.models.entity.DetallePedido;
import com.idat.currulo.web.models.entity.Pedido;
import com.idat.currulo.web.models.entity.Plato;
import com.idat.currulo.web.service.ClienteService;
import com.idat.currulo.web.service.DetallePedidoService;
import com.idat.currulo.web.service.PedidoService;
import com.idat.currulo.web.service.PlatoService;
import com.idat.currulo.web.util.reportes.PedidoExporterExcel;
import com.idat.currulo.web.util.reportes.PedidoExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class PedidoController {
	
	@Autowired
	private PedidoService service;
	
	@Autowired
	private DetallePedidoService detallePedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PlatoService productoService;
	
	@GetMapping(value = "/detallespedido/{idPedido}")
	public String verDetallesPedido(@PathVariable(value = "idPedido") Integer idPedido,
			Map<String, Object> modelo, RedirectAttributes flash) {
		Pedido pedido = service.obtenerPedido(idPedido);
		if (pedido == null) {
			flash.addAttribute("error", "¡El pedido no existe en la base de datos!");
			return "redirect:/pedidos";
		}
		List<DetallePedido> detallesPedido = detallePedidoService.obtenerDetallesPorPedido(pedido);
		modelo.put("pedido", pedido);
		modelo.put("detallesPedido", detallesPedido);
		modelo.put("Titulo", "Información del Pedido: PE000" + pedido.getIdPedido().toString());
		return "detallespedido";
	}
	
	@GetMapping(value = "/pedidos")
	public String gestionarpedidos(Model modelo) {
		modelo.addAttribute("listpedidos", service.listarPedidos());
		modelo.addAttribute("Titulo", "Pedidos");
		return "pedidos";
	}
	
	@GetMapping(value = "/pedidos/nuevoPedido")
	public String formularioPedido(Model modelo) {
		List<Cliente> clientes = clienteService.listarClientes();
		List<Plato> productos = productoService.listarPlatos();
		Pedido pedido = new Pedido();
		pedido.setFechaCompra(LocalDateTime.now());
		pedido.setTotal(0.00);
		pedido.setComentarios("N/A");
		modelo.addAttribute("pedido", pedido);
		modelo.addAttribute("clientes", clientes);
		modelo.addAttribute("productos", productos);
		return "formulario_pedido";
	}
	
	@PostMapping(value = "/pedidos")
	public String guardarPedido(@ModelAttribute("pedido") Pedido pedido,
								@RequestParam("idPlato") Integer[] idPlatos,
								@RequestParam("cantidad") Integer[] cantidades,
								@RequestParam("importe") Double[] importes) {
		service.guardarPedido(pedido);
		for (int i = 0; i < idPlatos.length; i++) {
			DetallePedido detalle = new DetallePedido();
			detalle.setPedido(pedido);
			Plato producto = productoService.obtenerPlato(idPlatos[i]);
			detalle.setPlato(producto);
			detalle.setCantidad(cantidades[i]);
			detalle.setImporte(importes[i]);	
			detallePedidoService.guardarDetalle(detalle);
		}
		return "redirect:/pedidos";
	}
	
	@GetMapping("/pedidos/editar/{idPedido}")
	public String formularioPedidoEditar(@PathVariable Integer idPedido, Model modelo) {
		List<Cliente> clientes = clienteService.listarClientes();
		List<Plato> productos = productoService.listarPlatos();
		modelo.addAttribute("clientes", clientes);
		modelo.addAttribute("productos", productos);
		modelo.addAttribute("pedido", service.obtenerPedido(idPedido));
		return "formulario_editarpedido";
	}
	
	@PostMapping(value = "/pedidos/{idPedido}")
	public String actualizarPedido(@PathVariable Integer idPedido, @ModelAttribute("pedido") Pedido pedido) {
		Pedido pedidoExistente = service.obtenerPedido(idPedido);
		pedidoExistente.setIdPedido(idPedido);
		pedidoExistente.setFechaCompra(pedido.getFechaCompra());
		pedidoExistente.setTotal(pedido.getTotal());
		pedidoExistente.setCliente(pedido.getCliente());
		pedidoExistente.setMetpago(pedido.getMetpago());
		pedidoExistente.setComentarios(pedido.getComentarios());
		boolean estadoPedido = pedido.getEstado() != null && pedido.getEstado();
		pedidoExistente.setEstado(estadoPedido);
		for (DetallePedido detalle : pedido.getItemsDetalle()) {
			DetallePedido detalleExistente = detallePedidoService.obtenerDetalle(detalle.getIdDetpedido());
			detalleExistente.setCantidad(detalle.getCantidad());
			detalleExistente.setImporte(detalle.getImporte());
			Plato producto = productoService.obtenerPlato(detalle.getPlato().getIdPlato());
			detalleExistente.setPlato(producto);
			detallePedidoService.actualizarDetalle(detalleExistente);
		}
		service.actualizarPedido(pedidoExistente);
		return "redirect:/pedidos";
	}
	
	@GetMapping(value = "/pedidos/{idPedido}")
	public String eliminarPedido(@PathVariable Integer idPedido) {
		service.eliminarPedido(idPedido);
		return "redirect:/pedidos";
	}
	
	@GetMapping(value = "/exportarPedidoPDF")
	public void exportarListadodePedidosenPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Pedidos_" + fechaActual + ".pdf";
		response.setHeader(cabecera, valor);
		List<Pedido> pedidos = service.listarPedidos();
		PedidoExporterPDF exporter = new PedidoExporterPDF(pedidos);
		exporter.exportarPDF(response);
	}
	
	@GetMapping(value = "/exportarPedidoExcel")
	public void exportarListadodePedidosenExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Pedidos_" + fechaActual + ".xlsx";
		response.setHeader(cabecera, valor);
		List<Pedido> pedidos = service.listarPedidos();
		PedidoExporterExcel exporter = new PedidoExporterExcel(pedidos);
		exporter.exportarExcel(response);
	}

}