package com.idat.currulo.web.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.idat.currulo.web.models.entity.Proveedor;
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

public class ProveedorExporterPDF {
	
	private List<Proveedor> listaProveedores;
	
	public ProveedorExporterPDF(List<Proveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}
	
	private void escribirCabeceradelaTabla(PdfPTable tabla) {
		Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuenteCabecera.setColor(Color.WHITE);
		fuenteCabecera.setSize(12);
		
		PdfPCell celda = getCenteredCell("ID", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Razón Social", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Dirección", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Email", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Teléfono", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Identificación", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("N° Documento", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
	}
	
	private void escribirDatosdelaTabla(PdfPTable tabla) {
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setSize(12);
		for (Proveedor proveedor : listaProveedores) {
			tabla.addCell(getCenteredCell(String.valueOf(proveedor.getIdProveedor()), fuente));
			tabla.addCell(getCenteredCell(proveedor.getRazonSocial(), fuente));
			tabla.addCell(getCenteredCell(proveedor.getDirProv(), fuente));
			tabla.addCell(getCenteredCell(proveedor.getEmailProv(), fuente));
			tabla.addCell(getCenteredCell(proveedor.getTelProv(), fuente));
			tabla.addCell(getCenteredCell(proveedor.getTipoidentificacion().getTipoIdent(), fuente));
			tabla.addCell(getCenteredCell(proveedor.getNumDocProv(), fuente));
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
		
		Paragraph titulo = new Paragraph("Lista de Proveedores", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(7);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setSpacingAfter(5f);
		tabla.setWidths(new float[] {1f, 2.5f, 2.5f, 2.5f, 2.0f, 2.4f, 2.5f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceradelaTabla(tabla);
		escribirDatosdelaTabla(tabla);
		
		documento.add(tabla);
		documento.close();
	}
	
}