package com.idat.currulo.web.util.reportes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.idat.currulo.web.models.entity.DetallePedido;
import com.idat.currulo.web.models.entity.Pedido;

public class PedidoExporterExcel {
	
	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<Pedido> listaPedidos;
	
	public PedidoExporterExcel(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Pedidos");
	}
	
	private void escribirCabeceradelaTabla() {
		Row fila = hoja.createRow(0);
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);
		
		Cell celda = fila.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(1);
		celda.setCellValue("Fecha de Realización");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Total");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Cliente");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Cantidad");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Importe");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("Producto");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(7);
		celda.setCellValue("Estado");
		celda.setCellStyle(estilo);
	}
	
	private void escribirDatosdelaTabla() {
		int nFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for (Pedido pedido : listaPedidos) {
			Row fila = hoja.createRow(nFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(pedido.getIdPedido());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(pedido.getFechaCompra().toString());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(pedido.getTotal());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(pedido.getCliente().getNomCli() + " " + pedido.getCliente().getApeCli());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			for (DetallePedido detallePedido : pedido.getItemsDetalle()) {
				celda = fila.createCell(4);
				celda.setCellValue(detallePedido.getCantidad());
				hoja.autoSizeColumn(4);
				celda.setCellStyle(estilo);
				
				celda = fila.createCell(5);
				celda.setCellValue(detallePedido.getImporte());
				hoja.autoSizeColumn(5);
				celda.setCellStyle(estilo);
				
				celda = fila.createCell(6);
				celda.setCellValue(detallePedido.getPlato().getNomPlato());
				hoja.autoSizeColumn(6);
				celda.setCellStyle(estilo);
			}
			
			celda = fila.createCell(7);
			celda.setCellValue(pedido.getEstado() ? "Entregado" : "Cancelado");
			hoja.autoSizeColumn(7);
			celda.setCellStyle(estilo);
		}
	}
	
	public void exportarExcel(HttpServletResponse response) throws IOException {
		escribirCabeceradelaTabla();
		escribirDatosdelaTabla();
		
		ServletOutputStream outputStream = response.getOutputStream();
		libro.write(outputStream);
		
		libro.close();
		outputStream.close();
	}
	
}