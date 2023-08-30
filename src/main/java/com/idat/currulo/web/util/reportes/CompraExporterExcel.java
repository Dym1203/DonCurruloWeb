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

import com.idat.currulo.web.models.entity.Compra;
import com.idat.currulo.web.models.entity.DetalleCompra;

public class CompraExporterExcel {
	
	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<Compra> listaCompras;
	
	public CompraExporterExcel(List<Compra> listaCompras) {
		this.listaCompras = listaCompras;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Compras");
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
		celda.setCellValue("Fecha de Compra");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Descripción");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Total");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Método de Pago");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Proveedor");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("Producto");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(7);
		celda.setCellValue("Cantidad");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(8);
		celda.setCellValue("Importe");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(9);
		celda.setCellValue("Estado");
		celda.setCellStyle(estilo);
	}
	
	private void escribirDatosdelaTabla() {
		int nFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for (Compra compra : listaCompras) {
			Row fila = hoja.createRow(nFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(compra.getIdCompra());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(compra.getFecha().toString());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(compra.getDescripcion());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(compra.getTotal().toString());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(compra.getMetpago());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(5);
			celda.setCellValue(compra.getProveedor().getRazonSocial());
			hoja.autoSizeColumn(5);
			celda.setCellStyle(estilo);
			
			for (DetalleCompra detalleCompra : compra.getItemsDetalleCompras()) {
				celda = fila.createCell(6);
				celda.setCellValue(detalleCompra.getPlato().getNomPlato());
				hoja.autoSizeColumn(6);
				celda.setCellStyle(estilo);
				
				celda = fila.createCell(7);
				celda.setCellValue(detalleCompra.getCantidad());
				hoja.autoSizeColumn(7);
				celda.setCellStyle(estilo);
				
				celda = fila.createCell(8);
				celda.setCellValue(detalleCompra.getImporte());
				hoja.autoSizeColumn(8);
				celda.setCellStyle(estilo);
			}
			
			celda = fila.createCell(9);
			celda.setCellValue(compra.getEstado() ? "En Proceso" : "Despachado");
			hoja.autoSizeColumn(9);
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