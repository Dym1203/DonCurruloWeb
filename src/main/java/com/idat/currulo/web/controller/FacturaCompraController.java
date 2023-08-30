package com.idat.currulo.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.idat.currulo.web.models.entity.Compra;
import com.idat.currulo.web.models.entity.DetalleCompra;
import com.idat.currulo.web.service.CompraService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class FacturaCompraController {
	
	@Autowired
	private CompraService compraService;
	
	@GetMapping("/generarFacturaCompra/{idCompra}")
	public String generarFactura(@PathVariable Integer idCompra, Model modelo) {
		Compra compra = compraService.obtenerCompra(idCompra);
		
		if (compra == null) {
			return "redirect:/compras";
		}
		
		Collection<DetalleCompra> detalles = compra.getItemsDetalleCompras();
		
		String jasperFilePath = "classpath:static/docs/facturacompra.jasper";
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idCompra", compra.getIdCompra());
		parametros.put("fecha", compra.getFecha());
		parametros.put("total", compra.getTotal());
		parametros.put("metpago", compra.getMetpago());
		parametros.put("proveedor", compra.getProveedor().getRazonSocial());
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detalles);
		
		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager.fillReport(jasperFilePath, parametros, dataSource);
		}
		catch (JRException e) {
			return "redirect:/compras";
		}
		
		byte[] pdfBytes;
		try {
			pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (JRException e) {
			return "redirect:/compras";
		}
		
		modelo.addAttribute("pdfBytes", pdfBytes);
		
		return "facturaCompra";
	}
	
}