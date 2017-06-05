package co.com.diccionario.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Categoria;
import co.com.diccionario.document.Ciudad;
import co.com.diccionario.document.Departamento;
import co.com.diccionario.document.Paises;
import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.mapper.CategoriaMapper;
import co.com.diccionario.mapper.CiudadMapper;
import co.com.diccionario.mapper.DepartamentoMapper;
import co.com.diccionario.mapper.PaisesMapper;
import co.com.diccionario.mongodb.repository.iface.CategoriaRepository;
import co.com.diccionario.mongodb.repository.iface.CiudadesRepository;
import co.com.diccionario.mongodb.repository.iface.DepartamentosRepository;
import co.com.diccionario.mongodb.repository.iface.PaisesRepository;
import co.com.diccionario.negocio.iface.ConsultarCatalogosIface;
import co.com.diccionario.utilidades.LevenshteinDistance;

@Service
public class ConsultarCatalogosImpl implements ConsultarCatalogosIface {

	@Autowired
	PaisesRepository paisesRepository;
	@Autowired
	DepartamentosRepository departamentosRepository;
	@Autowired
	CiudadesRepository ciudadesRepository;
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	Environment env;

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

	@Override
	public List<CategoriaDTO> obtenerCategorias() {
		List<Categoria> listCategoria = categoriaRepository.findAll();
		if (listCategoria != null && !listCategoria.isEmpty()) {
			List<CategoriaDTO> listCatDto = CategoriaMapper.INSTANCE.categoriasToCategoriaDTOs(listCategoria);
			return listCatDto;
		}
		return null;
	}

	@Override
	public List<CategoriaDTO> obtenerCategorias(String nombre) {
		List<Categoria> listCategoria = categoriaRepository.findByNombreIgnoreCase(nombre);
		List<CategoriaDTO> listCatDto;
		if (listCategoria != null && !listCategoria.isEmpty()) {
			listCatDto = CategoriaMapper.INSTANCE.categoriasToCategoriaDTOs(listCategoria);
			return listCatDto;
		} else {
			List<Categoria> listCategoriaAll = categoriaRepository.findAll();
			listCategoria = new ArrayList<>();
			String numCoincidencia = env.getProperty("num_coincidencia_palabra");
			int numCoinc = 2;
			if (numCoincidencia != null && !numCoincidencia.trim().isEmpty()) {
				numCoinc = Integer.parseInt(numCoincidencia);
			}

			for (Categoria categoria : listCategoriaAll) {
				int distance = LevenshteinDistance.computeLevenshteinDistance(nombre, categoria.getNombre());

				if (distance <= numCoinc) {
					listCategoria.add(categoria);
				}
			}
			if (listCategoria != null && !listCategoria.isEmpty()) {
				listCatDto = CategoriaMapper.INSTANCE.categoriasToCategoriaDTOs(listCategoria);
				return listCatDto;
			}
		}
		return null;
	}

}
