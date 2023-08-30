package com.idat.currulo.web.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.idat.currulo.web.models.entity.DetallePedido;
import com.idat.currulo.web.models.entity.Pedido;
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

public class PedidoExporterPDF {
	
	private List<Pedido> listaPedidos;
	
	public PedidoExporterPDF(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}
	
	private void escribirCabeceradelaTabla(PdfPTable tabla) {
		Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuenteCabecera.setColor(Color.WHITE);
		fuenteCabecera.setSize(12);
		
		PdfPCell celda = getCenteredCell("ID", fuenteCabecera);
		celda.setBackgroundColor(new Color(51, 102, 204));
        celda.setPadding(5);
        tabla.addCell(celda);
        
        celda = getCenteredCell("Fecha de Realización", fuenteCabecera);
        celda.setBackgroundColor(new Color(51, 102, 204));
        celda.setPadding(5);
        tabla.addCell(celda);

        celda = getCenteredCell("Total", fuenteCabecera);
        celda.setBackgroundColor(new Color(51, 102, 204));
        celda.setPadding(5);
        tabla.addCell(celda);
        
        celda = getCenteredCell("Cliente", fuenteCabecera);
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
        
        celda = getCenteredCell("Producto", fuenteCabecera);
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
		for (Pedido pedido : listaPedidos) {
			tabla.addCell(getCenteredCell(String.valueOf(pedido.getIdPedido()), fuente));
			tabla.addCell(getCenteredCell(String.valueOf(pedido.getFechaCompra()), fuente));
			tabla.addCell(getCenteredCell(String.valueOf("S/ " + pedido.getTotal()), fuente));
			tabla.addCell(getCenteredCell(pedido.getCliente().getNomCli() + " " + pedido.getCliente().getApeCli(), fuente));
			
			// Obtener los detalles del pedido y agregarlos a la tabla
	        for (DetallePedido detallePedido : pedido.getItemsDetalle()) {
	            tabla.addCell(getCenteredCell(String.valueOf(detallePedido.getCantidad()), fuente));
	            tabla.addCell(getCenteredCell(String.valueOf("S/ " + detallePedido.getImporte()), fuente));
	            tabla.addCell(getCenteredCell(detallePedido.getPlato().getNomPlato(), fuente));
	        }
			tabla.addCell(getCenteredCell(pedido.getEstado() ? "Entregado" : "Cancelado", fuente));
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
		
		Paragraph titulo = new Paragraph("Lista de Pedidos", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(8);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setSpacingAfter(5f);
		tabla.setWidths(new float[] {1f, 2.2f, 1.5f, 2.2f, 1.5f, 1.5f, 2.3f, 1.8f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceradelaTabla(tabla);
		escribirDatosdelaTabla(tabla);
		
		documento.add(tabla);
		documento.close();
	}

}