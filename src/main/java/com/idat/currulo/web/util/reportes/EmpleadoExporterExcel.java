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

import com.idat.currulo.web.models.entity.Empleado;

public class EmpleadoExporterExcel {
	
	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<Empleado> listaEmpleados;
	
	public EmpleadoExporterExcel(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Empleados");
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
		celda.setCellValue("Nombres");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Apellidos");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Sexo");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Teléfono");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Email");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("Cargo");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(7);
		celda.setCellValue("Fecha de Nacimiento");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(8);
		celda.setCellValue("Identificación");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(9);
		celda.setCellValue("N° Documento");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(10);
		celda.setCellValue("Salario");
		celda.setCellStyle(estilo);
	}
	
	private void escribirDatosdelaTabla() {
		int nFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for (Empleado empleado : listaEmpleados) {
			Row fila = hoja.createRow(nFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(empleado.getIdEmpleado());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(empleado.getNomEmp());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(empleado.getApeEmp());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(empleado.getSexo());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(empleado.getTelEmp());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(5);
			celda.setCellValue(empleado.getEmailEmp());
			hoja.autoSizeColumn(5);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(6);
			celda.setCellValue(empleado.getCargo());
			hoja.autoSizeColumn(6);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(7);
			celda.setCellValue(empleado.getFechanacimiento().toString());
			hoja.autoSizeColumn(7);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(8);
			celda.setCellValue(empleado.getTipoidentificacion().getTipoIdent());
			hoja.autoSizeColumn(8);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(9);
			celda.setCellValue(empleado.getNumDocEmp());
			hoja.autoSizeColumn(9);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(10);
			celda.setCellValue(empleado.getSalario().toString());
			hoja.autoSizeColumn(10);
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