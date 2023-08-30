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

import com.idat.currulo.web.models.entity.Proveedor;

public class ProveedorExporterExcel {
	
	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<Proveedor> listaProveedores;
	
	public ProveedorExporterExcel(List<Proveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Proveedores");
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
		celda.setCellValue("Razón Social");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Dirección");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Email");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Teléfono");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Identificación");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("N° Documento");
		celda.setCellStyle(estilo);
	}
	
	private void escribirDatosdelaTabla() {
		int nFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for (Proveedor proveedor : listaProveedores) {
			Row fila = hoja.createRow(nFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(proveedor.getIdProveedor());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(proveedor.getRazonSocial());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(proveedor.getDirProv());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(proveedor.getEmailProv());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(proveedor.getTelProv());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(5);
			celda.setCellValue(proveedor.getTipoidentificacion().getTipoIdent());
			hoja.autoSizeColumn(5);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(6);
			celda.setCellValue(proveedor.getNumDocProv());
			hoja.autoSizeColumn(6);
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