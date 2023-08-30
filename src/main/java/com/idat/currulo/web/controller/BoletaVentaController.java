package com.idat.currulo.web.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.idat.currulo.web.models.entity.DetallePedido;
import com.idat.currulo.web.models.entity.Pedido;
import com.idat.currulo.web.service.PedidoService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class BoletaVentaController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@GetMapping("/generarBoletaVenta/{idPedido}")
	public String generarBoleta(@PathVariable Integer idPedido, Model modelo) throws IOException {
		Pedido pedido = pedidoService.obtenerPedido(idPedido);
		
		if (pedido == null) {
			return "redirect:/pedidos";
		}
		
		Collection<DetallePedido> detalles = pedido.getItemsDetalle();
		
		String imagenPath = "classpath:static/images/logocurrulo.png";
		String jasperFilePath = "classpath:static/docs/boletaventa.jasper";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String fechaCompraStr = pedido.getFechaCompra().format(formatter);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idPedido", pedido.getIdPedido());
		parametros.put("fechaCompra", fechaCompraStr);
		parametros.put("total", pedido.getTotal());
		parametros.put("cliente", pedido.getCliente().getNomCli() + " " + pedido.getCliente().getApeCli());
		parametros.put("correo", pedido.getCliente().getEmailCli());
		parametros.put("telefono", pedido.getCliente().getTelCli());
		parametros.put("documento", pedido.getCliente().getNumDoc());
		parametros.put("imagenUrl", resourceLoader.getResource(imagenPath).getURI().toString());
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detalles);
		
		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager.fillReport(jasperFilePath, parametros, dataSource);
		}
		catch (JRException e) {
			return "redirect:/pedidos";
		}
		
		byte[] pdfBytes;
		try {
			pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (JRException e) {
			return "redirect:/pedidos";
		}
		
		modelo.addAttribute("pdfBytes", pdfBytes);
		
		return "boletaVenta";
	}
	
}