package co.com.diccionario.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Categoria;
import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.mapper.CategoriaMapper;
import co.com.diccionario.mongodb.iface.CategoriaIface;
import co.com.diccionario.mongodb.repository.iface.CategoriaRepository;
import co.com.diccionario.negocio.cacheable.iface.CategoriasCacheableIface;
import co.com.diccionario.negocio.iface.GestionarBusquedaCategoriaIface;
import co.com.diccionario.utilidades.LevenshteinDistance;

@Service
public class GestionarBusquedaCategoriaImpl implements GestionarBusquedaCategoriaIface {

	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	CategoriaIface categoriaIface;
	@Autowired
	CategoriasCacheableIface categoriasCacheableIface;
	@Autowired
	Environment env;

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

		List<CategoriaDTO> listCategoriaAll = categoriasCacheableIface.obtenerCategorias();
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
