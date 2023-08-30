package com.idat.currulo.web.service;

import java.util.List;

import com.idat.currulo.web.models.entity.Empleado;

public interface EmpleadoService {
	
	public List<Empleado> listarEmpleados();
	
	public Empleado guardarEmpleado(Empleado empleado);
	
    public Empleado obtenerEmpleado(Integer idEmpleado);
    
    public Empleado actualizarEmpleado(Empleado empleado);
    
    public void eliminarEmpleado(Integer idEmpleado);
	
}