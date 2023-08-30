package com.idat.currulo.web.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.idat.currulo.web.models.entity.Compra;
import com.idat.currulo.web.models.entity.DetalleCompra;
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

public class CompraExporterPDF {
	
	private List<Compra> listaCompras;
	
	public CompraExporterPDF(List<Compra> listaCompras) {
		this.listaCompras = listaCompras;
	}
	
	private void escribirCabeceradelaTabla(PdfPTable tabla) {
		Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuenteCabecera.setColor(Color.WHITE);
		fuenteCabecera.setSize(12);
		
		PdfPCell celda = getCenteredCell("ID", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Fecha de Compra", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Total", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Método de Pago", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Proveedor", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Producto", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Cantidad", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Importe", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
		
		celda = getCenteredCell("Estado", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
		celda.setPadding(5);
		tabla.addCell(celda);
	}
	
	private void escribirDatosdelaTabla(PdfPTable tabla) {
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setSize(12);
		for (Compra compra : listaCompras) {
			tabla.addCell(getCenteredCell(String.valueOf(compra.getIdCompra()), fuente));
			tabla.addCell(getCenteredCell(String.valueOf(compra.getFecha()), fuente));
			tabla.addCell(getCenteredCell(String.valueOf("S/ " + compra.getTotal()), fuente));
			tabla.addCell(getCenteredCell(compra.getMetpago(), fuente));
			tabla.addCell(getCenteredCell(compra.getProveedor().getRazonSocial(), fuente));
			// Obtener los detalles de la compra y agregarlos a la tabla
			for (DetalleCompra detalleCompra : compra.getItemsDetalleCompras()) {
				tabla.addCell(getCenteredCell(detalleCompra.getPlato().getNomPlato(), fuente));
				tabla.addCell(getCenteredCell(String.valueOf(detalleCompra.getCantidad()), fuente));
				tabla.addCell(getCenteredCell(String.valueOf("S/ " + detalleCompra.getImporte()), fuente));
			}
			tabla.addCell(getCenteredCell(compra.getEstado() ? "En Proceso" : "Despachado", fuente));
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
		
		Paragraph titulo = new Paragraph("Lista de Compras", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(9);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setSpacingAfter(5f);
		tabla.setWidths(new float[] {1f, 1.8f, 1.5f, 1.8f, 2.0f, 2.0f, 1.8f, 1.5f, 1.9f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceradelaTabla(tabla);
		escribirDatosdelaTabla(tabla);
		
		documento.add(tabla);
		documento.close();
	}
	
}