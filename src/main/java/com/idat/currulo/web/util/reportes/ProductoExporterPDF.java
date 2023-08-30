package com.idat.currulo.web.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.idat.currulo.web.models.entity.Plato;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ProductoExporterPDF {
	
	private List<Plato> listaProductos;
	
	public ProductoExporterPDF(List<Plato> listaProductos) {
		this.listaProductos = listaProductos;
	}
	
	private void escribirCabeceradelaTabla(PdfPTable tabla) {
		Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuenteCabecera.setColor(Color.WHITE);
		fuenteCabecera.setSize(12);
		
		PdfPCell celda = getCenteredCell("ID", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Nombre", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Descripción", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Precio", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Cantidad", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Categoría", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
	}
	
	private void escribirDatosdelaTabla(PdfPTable tabla) {
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setSize(12);
		for (Plato producto : listaProductos) {
			tabla.addCell(getCenteredCell(String.valueOf(producto.getIdPlato()), fuente));
			tabla.addCell(getCenteredCell(producto.getNomPlato(), fuente));
			tabla.addCell(getCenteredCell(producto.getDescPlato(), fuente));
			tabla.addCell(getCenteredCell(String.valueOf("S/ " + producto.getPreciou()), fuente));
			tabla.addCell(getCenteredCell(String.valueOf(producto.getStock()), fuente));
			tabla.addCell(getCenteredCell(producto.getCategoria().getNomCategoria(), fuente));
		}
	}
	
	private PdfPCell getCenteredCell(String text, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		return cell;
	}
	
	public void exportarPDF(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		
		documento.open();
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(new Color(51, 102, 204));
		fuente.setSize(18);
		
		Paragraph titulo = new Paragraph("Lista de Productos", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(6);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setSpacingAfter(5f);
		tabla.setWidths(new float[] {1f, 2.3f, 2.6f, 2.0f, 2.0f, 2.5f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceradelaTabla(tabla);
		escribirDatosdelaTabla(tabla);
		
		documento.add(tabla);
		documento.close();
	}

}