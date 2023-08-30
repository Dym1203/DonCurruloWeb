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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idat.currulo.web.models.entity.Empleado;
import com.idat.currulo.web.models.entity.TipoIdentificacion;
import com.idat.currulo.web.models.entity.Usuario;
import com.idat.currulo.web.service.EmpleadoService;
import com.idat.currulo.web.service.TipoIdentificacionService;
import com.idat.currulo.web.service.UsuarioService;
import com.idat.currulo.web.util.reportes.EmpleadoExporterExcel;
import com.idat.currulo.web.util.reportes.EmpleadoExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService service;
	
	@Autowired
	private TipoIdentificacionService identificacionService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(value = "/detallesempleado/{idEmpleado}")
	public String verDetallesEmpleado(@PathVariable(value = "idEmpleado") Integer idEmpleado,
			Map<String, Object> modelo, RedirectAttributes flash) {
		Empleado empleado = service.obtenerEmpleado(idEmpleado);
		if (empleado == null) {
			flash.addAttribute("error", "¡El empleado no existe en la base de datos!");
			return "redirect:/empleados";
		}
		modelo.put("empleado", empleado);
		modelo.put("Titulo", "Detalles del Empleado: " + empleado.getNomEmp() + " " + empleado.getApeEmp());
		return "detallesempleado";
	}
	
	@GetMapping(value = "/empleados")
	public String gestionarempleados(Model modelo) {
		modelo.addAttribute("listempleados", service.listarEmpleados());
		modelo.addAttribute("Titulo", "Empleados");
		return "empleados";
	}
	
	@GetMapping(value = "/empleados/nuevoEmpleado")
	public String formularioEmpleado(Model modelo) {
		List<TipoIdentificacion> identificaciones = identificacionService.listarIdentificaciones();
		List<Usuario> usuarios = usuarioService.listarUsuarios();
		Empleado empleado = new Empleado ();
		modelo.addAttribute("empleado", empleado);
		modelo.addAttribute("identificaciones", identificaciones);
		modelo.addAttribute("usuarios", usuarios);
		return "formulario_empleado";
	}
	
	/*@PostMapping(value = "/empleados")
	public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado, @RequestParam("file") MultipartFile imagen) {
		if (!imagen.isEmpty()) {
			try {
				Path directorioImagenes = Paths.get("src//main//resources//static/uploads");
				String rootPath = directorioImagenes.toFile().getAbsolutePath();
				byte[] bytes = imagen.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				empleado.setImagen(imagen.getOriginalFilename());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		service.guardarEmpleado(empleado);
		return "redirect:/empleados";
	}*/
	
	@PostMapping(value = "/empleados")
	public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
		service.guardarEmpleado(empleado);
		return "redirect:/empleados";
	}
	
	@GetMapping("/empleados/editar/{idEmpleado}")
	public String formularioEmpleadoEditar(@PathVariable Integer idEmpleado, Model modelo) {
		List<TipoIdentificacion> identificaciones = identificacionService.listarIdentificaciones();
		List<Usuario> usuarios = usuarioService.listarUsuarios();
		modelo.addAttribute("identificaciones", identificaciones);
		modelo.addAttribute ("usuarios", usuarios);
		modelo.addAttribute("empleado", service.obtenerEmpleado(idEmpleado));
		return "formulario_editarempleado";
	}
	
	@PostMapping(value = "/empleados/{idEmpleado}")
	public String actualizarEmpleado (@PathVariable Integer idEmpleado, @ModelAttribute("empleado") Empleado empleado, Model modelo) {
		Empleado empleadoExistente = service.obtenerEmpleado(idEmpleado);
		empleadoExistente.setIdEmpleado(idEmpleado);
		empleadoExistente.setNomEmp(empleado.getNomEmp());
		empleadoExistente.setApeEmp(empleado.getApeEmp());
		empleadoExistente.setSexo(empleado.getSexo());
		empleadoExistente.setTelEmp(empleado.getTelEmp());
		empleadoExistente.setEmailEmp(empleado.getEmailEmp());
		empleadoExistente.setCargo(empleado.getCargo());
		empleadoExistente.setFechanacimiento(empleado.getFechanacimiento());
		empleadoExistente.setTipoidentificacion(empleado.getTipoidentificacion());
		empleadoExistente.setNumDocEmp(empleado.getNumDocEmp());
		empleadoExistente.setUsuario(empleado.getUsuario());
		empleadoExistente.setSalario(empleado.getSalario());
		empleadoExistente.setImagen(empleado.getImagen());
		boolean estadoEmpleado = empleado.getEstado() != null && empleado.getEstado();
		empleadoExistente.setEstado(estadoEmpleado);
		service.actualizarEmpleado(empleadoExistente);
		return "redirect:/empleados";
	}
	
	@GetMapping(value = "/empleados/{idEmpleado}")
	public String eliminarEmpleado(@PathVariable Integer idEmpleado) {
		service.eliminarEmpleado(idEmpleado);
		return "redirect:/empleados";
	}
	
	@GetMapping(value = "/exportarEmpleadoPDF")
	public void exportarListadodeEmpleadosenPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";
		response.setHeader(cabecera, valor);
		List<Empleado> empleados = service.listarEmpleados();
		EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleados);
		exporter.exportarPDF(response);
	}
	
	@GetMapping(value = "/exportarEmpleadoExcel")
	public void exportarListadodeEmpleadosenExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".xlsx";
		response.setHeader(cabecera, valor);
		List<Empleado> empleados = service.listarEmpleados();
		EmpleadoExporterExcel exporter = new EmpleadoExporterExcel(empleados);
		exporter.exportarExcel(response);
	}

}