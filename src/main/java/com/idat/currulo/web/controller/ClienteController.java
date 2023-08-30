package com.idat.currulo.web.controller;

import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.idat.currulo.web.models.entity.Cliente;
import com.idat.currulo.web.models.entity.TipoIdentificacion;
import com.idat.currulo.web.models.entity.Usuario;
import com.idat.currulo.web.service.ClienteService;
import com.idat.currulo.web.service.TipoIdentificacionService;
import com.idat.currulo.web.service.UsuarioService;
import com.idat.currulo.web.util.reportes.ClienteExporterExcel;
import com.idat.currulo.web.util.reportes.ClienteExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@Autowired
	private TipoIdentificacionService identificacionService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(value = "/detallescliente/{idCliente}")
	public String verDetallesCliente(@PathVariable(value = "idCliente") Integer idCliente, Map<String, Object> modelo,
										RedirectAttributes flash) {
		Cliente cliente = service.obtenerCliente(idCliente);
		if (cliente == null) {
			flash.addAttribute("error", "¡El cliente no existe en la base de datos!");
			return "redirect:/clientes";
		}
		modelo.put("cliente", cliente);
		modelo.put("Titulo", "Detalles del Cliente: " + cliente.getNomCli() + " " + cliente.getApeCli());
		return "detallescliente";
	}
	
	@GetMapping(value = "/clientes")
	public String listadoClientes(Model modelo) {
		modelo.addAttribute("listclientes", service.listarClientes());
		modelo.addAttribute("Titulo", "Clientes");
		return "clientes";
	}
	
	@GetMapping(value = "/clientes/nuevoCliente")
	public String formularioCliente(Model modelo) {
		List<TipoIdentificacion> identificaciones = identificacionService.listarIdentificaciones();
		List<Usuario> usuarios = usuarioService.listarUsuarios();
		Cliente cliente = new Cliente();
		modelo.addAttribute("cliente", cliente);
		modelo.addAttribute("identificaciones", identificaciones);
		modelo.addAttribute("usuarios", usuarios);
		return "formulario_cliente";
	}
	
	@PostMapping(value = "/clientes")
	public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
		service.guardarCliente(cliente);
		return "redirect:/clientes";
	}
	
	/*@PostMapping(value = "/clientes")
	public String guardarCliente(@ModelAttribute("cliente") Cliente cliente, @RequestParam("imagen") MultipartFile imagen) {
		if (!imagen.isEmpty()) {
			try {
				Path directorioImagenes = Paths.get("src//main//resources//static/uploads");
				String rootPath = directorioImagenes.toFile().getAbsolutePath();
				byte[] bytes = imagen.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				cliente.setImagen(imagen.getOriginalFilename());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		service.guardarCliente(cliente);
		return "redirect:/clientes";
	}*/
	
	@GetMapping("/clientes/editar/{idCliente}")
	public String formularioClienteEditar(@PathVariable Integer idCliente, Model modelo) {
		List<TipoIdentificacion> identificaciones = identificacionService.listarIdentificaciones();
		List<Usuario> usuarios = usuarioService.listarUsuarios();
		modelo.addAttribute("identificaciones", identificaciones);
		modelo.addAttribute("usuarios", usuarios);
		modelo.addAttribute("cliente", service.obtenerCliente(idCliente));
		return "formulario_editarcliente";
	}
	
	@PostMapping(value = "/clientes/{idCliente}")
	public String actualizarCliente(@PathVariable Integer idCliente, @ModelAttribute("cliente") Cliente cliente, Model modelo) {
		Cliente clienteExistente = service.obtenerCliente(idCliente);
		clienteExistente.setIdCliente(idCliente);
		clienteExistente.setNomCli(cliente.getNomCli());
		clienteExistente.setApeCli(cliente.getApeCli());
		clienteExistente.setSexo(cliente.getSexo());
		clienteExistente.setDirCli(cliente.getDirCli());
		clienteExistente.setTelCli(cliente.getTelCli());
		clienteExistente.setTipoidentificacion(cliente.getTipoidentificacion());
		clienteExistente.setNumDoc(cliente.getNumDoc());
		clienteExistente.setEmailCli(cliente.getEmailCli());
		clienteExistente.setImagen(cliente.getImagen());
		clienteExistente.setUsuario(cliente.getUsuario());
		boolean estadoCliente = cliente.getEstado() != null && cliente.getEstado();
		clienteExistente.setEstado(estadoCliente);
		service.actualizarCliente(clienteExistente);
		return "redirect:/clientes";
	}
	
	@GetMapping(value = "/clientes/{idCliente}")
	public String eliminarCliente(@PathVariable Integer idCliente) {
		service.eliminarCliente(idCliente);
		return "redirect:/clientes";
	}
	
	@GetMapping(value = "/exportarClientePDF")
	public void exportarListadodeClientesenPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Clientes_" + fechaActual + ".pdf";
		response.setHeader(cabecera, valor);
		List<Cliente> clientes = service.listarClientes();
		ClienteExporterPDF exporter = new ClienteExporterPDF(clientes);
		exporter.exportarPDF(response);
	}
	
	@GetMapping(value = "/exportarClienteExcel")
	public void exportarListadodeClientesenExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Clientes_" + fechaActual + ".xlsx";
		response.setHeader(cabecera, valor);
		List<Cliente> clientes = service.listarClientes();
		ClienteExporterExcel exporter = new ClienteExporterExcel(clientes);
		exporter.exportarExcel(response);
	}
	
}