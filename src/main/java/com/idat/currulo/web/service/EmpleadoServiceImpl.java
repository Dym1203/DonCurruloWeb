package com.idat.currulo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.currulo.web.models.entity.Empleado;
import com.idat.currulo.web.models.repository.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> listarEmpleados() {
		return empleadoRepository.findAll();
	}
	
	@Override
	@Transactional
	public Empleado guardarEmpleado(Empleado empleado) {
	    return empleadoRepository.save(empleado);
	}

	@Override
	@Transactional(readOnly = true)
	public Empleado obtenerEmpleado(Integer idEmpleado) {
	    return empleadoRepository.findById(idEmpleado).get();
	}

	@Override
	@Transactional
	public Empleado actualizarEmpleado(Empleado empleado) {
	    return empleadoRepository.save(empleado);
	}

	@Override
	@Transactional
	public void eliminarEmpleado(Integer idEmpleado) {
	    empleadoRepository.deleteById(idEmpleado);
	}
	
}