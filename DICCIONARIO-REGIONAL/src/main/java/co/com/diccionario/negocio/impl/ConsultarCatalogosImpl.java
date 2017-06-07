package co.com.diccionario.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import co.com.diccionario.mongodb.iface.CategoriaIface;
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
	@Autowired
	CategoriaIface categoriaIface;

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
	@Cacheable("categorias")
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
		}
		return null;
	}

	@Override
	public List<CategoriaDTO> obtenerCategoriasAproximado(String nombre) {
		List<Categoria> listCategoria = categoriaIface.findByNombreLike(nombre);
		List<CategoriaDTO> listCatDto = new ArrayList<>();
		if (listCategoria != null && !listCategoria.isEmpty()) {
			listCatDto = CategoriaMapper.INSTANCE.categoriasToCategoriaDTOs(listCategoria);
		}

		List<CategoriaDTO> listCategoriaAll = obtenerCategorias();
		String numCoincidencia = env.getProperty("num_coincidencia_palabra");
		int numCoinc = 2;
		if (numCoincidencia != null && !numCoincidencia.trim().isEmpty()) {
			numCoinc = Integer.parseInt(numCoincidencia);
		}

		for (CategoriaDTO categoria : listCategoriaAll) {
			int distance = LevenshteinDistance.computeLevenshteinDistance(nombre, categoria.getNombre());
			if (distance <= numCoinc) {
				listCatDto.add(categoria);
			}

			String[] sWords = categoria.getNombre().split(" ");
			if (sWords.length > 1) {
				for (String palabra : sWords) {
					distance = LevenshteinDistance.computeLevenshteinDistance(nombre, palabra);
					if (distance <= numCoinc) {
						if (listCatDto.contains(categoria)) {
							continue;
						}
						listCatDto.add(categoria);
					}
				}
			}

			sWords = nombre.split(" ");
			if (sWords.length > 1) {
				for (String palabra : sWords) {
					String[] cat = categoria.getNombre().split(" ");
					boolean validarPalabraAproximada = false;
					if (cat.length > 1) {
						validarPalabraAproximada = validarPalabraAproximada(categoria.getNombre(), palabra, numCoinc);
					}

					if (validarPalabraAproximada) {
						if (listCatDto.contains(categoria)) {
							continue;
						}
						listCatDto.add(categoria);
					}

					distance = LevenshteinDistance.computeLevenshteinDistance(palabra, categoria.getNombre());
					if (distance <= numCoinc) {
						if (listCatDto.contains(categoria)) {
							continue;
						}
						listCatDto.add(categoria);
					}
				}
			}

			String sTexto = categoria.getNombre();
			int encontro = sTexto.indexOf(nombre);
			if (encontro == -1) {
				continue;
			}

			if (listCatDto.contains(categoria)) {
				continue;
			}

			if (sTexto.length() > 1) {
				sTexto = sTexto.substring(sTexto.indexOf(nombre), sTexto.length());
				while (sTexto.indexOf(nombre) > -1) {
					sTexto = sTexto.substring(sTexto.indexOf(nombre), sTexto.length());
					if (listCatDto.contains(categoria)) {
						break;
					}
					listCatDto.add(categoria);
				}
			}
		}

		return listCatDto;
	}

	private boolean validarPalabraAproximada(String word, String nombre, int numCoinc) {
		String[] sWords = word.split(" ");
		if (sWords.length > 1) {
			for (String palabra : sWords) {
				int distance = LevenshteinDistance.computeLevenshteinDistance(nombre, palabra);
				if (distance <= numCoinc) {
					return true;
				}
			}
		}
		return false;
	}

}
