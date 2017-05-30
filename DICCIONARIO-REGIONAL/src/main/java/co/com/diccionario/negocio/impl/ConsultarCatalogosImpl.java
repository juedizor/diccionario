package co.com.diccionario.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Ciudad;
import co.com.diccionario.document.Departamento;
import co.com.diccionario.document.Paises;
import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.mapper.CiudadMapper;
import co.com.diccionario.mapper.DepartamentoMapper;
import co.com.diccionario.mapper.PaisesMapper;
import co.com.diccionario.mongodb.repository.iface.CiudadesRepository;
import co.com.diccionario.mongodb.repository.iface.DepartamentosRepository;
import co.com.diccionario.mongodb.repository.iface.PaisesRepository;
import co.com.diccionario.negocio.iface.ConsultarCatalogosIface;

@Service
public class ConsultarCatalogosImpl implements ConsultarCatalogosIface {

	@Autowired
	PaisesRepository paisesRepository;
	@Autowired
	DepartamentosRepository departamentosRepository;
	@Autowired
	CiudadesRepository ciudadesRepository;

	@Override
	public List<PaisesDTO> obtenerPaises() {
		List<Paises> listPaises = paisesRepository.findAll();
		if (listPaises != null && !listPaises.isEmpty()) {
			List<PaisesDTO> listPaisesDTO = PaisesMapper.INSTANCE.paisesToPaisesDTOs(listPaises);
			return listPaisesDTO;
		}
		return null;
	}

	@Override
	public List<PaisesDTO> obtenerPaises(String nombre) {
		List<Paises> listPaises = paisesRepository.findByNombreIgnoreCase(nombre);
		if (listPaises != null && !listPaises.isEmpty()) {
			List<PaisesDTO> listPaisesDTO = PaisesMapper.INSTANCE.paisesToPaisesDTOs(listPaises);
			return listPaisesDTO;
		}
		return null;
	}

	@Override
	public List<DepartamentoDTO> obtenerDepartamentos(int idPais) {
		List<Departamento> listDepa = departamentosRepository.findByPais(idPais);
		if (listDepa != null && !listDepa.isEmpty()) {
			List<DepartamentoDTO> listDepaDTO = DepartamentoMapper.INSTANCE.departamentosToDepartamentoDTOs(listDepa);
			return listDepaDTO;
		}
		return null;
	}

	@Override
	public List<DepartamentoDTO> obtenerDepartamentos(int idPais, String nombre) {
		List<Departamento> listDepa = departamentosRepository.findByPaisAndNombreIgnoreCase(idPais, nombre);
		if (listDepa != null && !listDepa.isEmpty()) {
			List<DepartamentoDTO> listDepaDTO = DepartamentoMapper.INSTANCE.departamentosToDepartamentoDTOs(listDepa);
			return listDepaDTO;
		}
		return null;
	}

	@Override
	public List<CiudadDTO> obtenerCiudades(int departamento) {
		List<Ciudad> listCiudad = ciudadesRepository.findByDepartamento(departamento);
		if (listCiudad != null && !listCiudad.isEmpty()) {
			List<CiudadDTO> listCiudadDTO = CiudadMapper.INSTANCE.ciudadesToCiudadDTOs(listCiudad);
			return listCiudadDTO;
		}

		return null;
	}

	@Override
	public List<CiudadDTO> obtenerCiudades(int departamento, String nombre) {
		List<Ciudad> listCiudad = ciudadesRepository.findByDepartamentoAndNombreIgnoreCase(departamento, nombre);
		if (listCiudad != null && !listCiudad.isEmpty()) {
			List<CiudadDTO> listCiudadDTO = CiudadMapper.INSTANCE.ciudadesToCiudadDTOs(listCiudad);
			return listCiudadDTO;
		}

		return null;
	}

}
