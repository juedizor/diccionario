package co.com.diccionario.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.mapper.SinonimosMapper;
import co.com.diccionario.mongodb.repository.iface.SinonimosRepository;
import co.com.diccionario.negocio.cacheable.iface.SinonimosCachableIface;
import co.com.diccionario.negocio.iface.GestionarBusquedaPalabrasIface;
import co.com.diccionario.utilidades.LevenshteinDistance;

@Service
public class GestionarBusquedaPalabrasImpl implements GestionarBusquedaPalabrasIface {

	@Autowired
	SinonimosRepository sinonimosRepository;
	@Autowired
	SinonimosCachableIface sinonimosCachableIface;
	@Autowired
	Environment env;

	@Override
	public List<SinonimosDTO> obtenerPalabrasPorTerminoCategoria(ParamsBusquedaPalabraDTO params) {

		List<SinonimosDTO> listSinonimosEncontrados = buscarPorCategoriaTermino(params);
		if (listSinonimosEncontrados != null) {
			return listSinonimosEncontrados;
		}

		listSinonimosEncontrados = buscarPorTermino(params);
		if (listSinonimosEncontrados != null) {
			return listSinonimosEncontrados;
		}

		/**
		 * en este punto no se encontro nada por los filtros seleccionados, se
		 * busca la palabra en el sistema con a mayor proximidas posible
		 */

		String palabra = params.getTermino();
		if (palabra == null || palabra.trim().isEmpty()) {
			return null;
		}

		List<Sinonimos> listSinonimosAll = sinonimosRepository.findByPaisOrigenAndPaisDestino(
				params.getPaisOrigen().getNombre(), params.getPaisDestino().getNombre());
		if (listSinonimosAll != null && !listSinonimosAll.isEmpty()) {
			listSinonimosEncontrados = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listSinonimosAll);
			listSinonimosEncontrados = buscarPalabraAproximada(palabra.trim(), listSinonimosEncontrados);
			return listSinonimosEncontrados;
		}

		return listSinonimosEncontrados;

	}

	/**
	 * realiza la busqueda por pais origen, pais destino, departamento origen,
	 * departamento destino el termino y su categoria
	 * 
	 * @param params
	 * @return
	 */
	public List<SinonimosDTO> buscarTeniendoDepartamentos(ParamsBusquedaPalabraDTO params) {
		PaisesDTO paisDestinoDTO = params.getPaisDestino();
		PaisesDTO paisOrigenDTO = params.getPaisOrigen();

		if (paisDestinoDTO == null || paisOrigenDTO == null) {
			return null;
		}

		DepartamentoDTO departamentoOrigenDTO = params.getDepartamentoOrigen();
		DepartamentoDTO departamentoDestinoDTO = params.getDepartamentoDestino();

		if (departamentoOrigenDTO == null || departamentoDestinoDTO == null) {
			return null;
		}

		String termino = params.getTermino();
		if (termino == null || termino.trim().isEmpty()) {
			return null;
		}

		String categoria = params.getCategoria();
		if (categoria == null || categoria.trim().isEmpty()) {
			return null;
		}

		String paisOrigen = params.getPaisOrigen().getNombre();
		String paisDestino = params.getPaisDestino().getNombre();
		if (paisOrigen == null || paisDestino == null || paisOrigen.trim().isEmpty() || paisDestino.trim().isEmpty()) {
			return null;
		}

		String departamentoOrigen = params.getDepartamentoOrigen().getNombre();
		String departamentoDestino = params.getDepartamentoDestino().getNombre();

		if (departamentoOrigen == null || departamentoDestino == null || departamentoOrigen.trim().isEmpty()
				|| departamentoDestino.trim().isEmpty()) {
			return null;
		}

		List<Sinonimos> listSinonimos = sinonimosRepository
				.findByPaisOrigenAndPaisDestinoAndDepartamentoOrigenAndDepartamentoDestinoAndTerminoAndCategoria(
						paisOrigen, paisDestino, departamentoOrigen, departamentoDestino, termino, categoria);
		if (listSinonimos != null && !listSinonimos.isEmpty()) {
			List<SinonimosDTO> listSinonimosDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listSinonimos);
			return listSinonimosDTO;
		}
		return null;
	}

	/**
	 * realiza la busqueda por los dos paises y el termino
	 * 
	 * @param params
	 * @return
	 */
	public List<SinonimosDTO> buscarPorTermino(ParamsBusquedaPalabraDTO params) {
		PaisesDTO paisDestinoDTO = params.getPaisDestino();
		PaisesDTO paisOrigenDTO = params.getPaisOrigen();

		if (paisDestinoDTO == null || paisOrigenDTO == null) {
			return null;
		}

		String termino = params.getTermino();
		if (termino == null || termino.trim().isEmpty()) {
			return null;
		}

		String paisOrigen = params.getPaisOrigen().getNombre();
		String paisDestino = params.getPaisDestino().getNombre();

		if (paisOrigen == null || paisDestino == null || paisOrigen.trim().isEmpty() || paisDestino.trim().isEmpty()) {
			return null;
		}

		List<Sinonimos> listSinonimos = sinonimosRepository.findByPaisOrigenAndPaisDestinoAndTermino(paisOrigen,
				paisDestino, termino);
		if (listSinonimos != null && !listSinonimos.isEmpty()) {
			List<SinonimosDTO> listSinonimosDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listSinonimos);
			return listSinonimosDTO;
		}
		return null;
	}

	/**
	 * realiza la busqueda por los dos paises y la categoria
	 * 
	 * @param params
	 * @return
	 */
	public List<SinonimosDTO> buscarPorCategoria(ParamsBusquedaPalabraDTO params) {

		PaisesDTO paisDestinoDTO = params.getPaisDestino();
		PaisesDTO paisOrigenDTO = params.getPaisOrigen();

		if (paisDestinoDTO == null || paisOrigenDTO == null) {
			return null;
		}

		String categoria = params.getCategoria();
		if (categoria == null || categoria.trim().isEmpty()) {
			return null;
		}

		String paisOrigen = params.getPaisOrigen().getNombre();
		String paisDestino = params.getPaisDestino().getNombre();

		if (paisOrigen == null || paisDestino == null || paisOrigen.trim().isEmpty() || paisDestino.trim().isEmpty()) {
			return null;
		}

		List<Sinonimos> listSinonimos = sinonimosRepository.findByPaisOrigenAndPaisDestinoAndCategoria(paisOrigen,
				paisDestino, categoria);
		if (listSinonimos != null && !listSinonimos.isEmpty()) {
			List<SinonimosDTO> listSinonimosDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listSinonimos);
			return listSinonimosDTO;
		}
		return null;
	}

	/**
	 * realiza la busqueda teniendo los dos paises, la categoria y el termino
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<SinonimosDTO> buscarPorCategoriaTermino(ParamsBusquedaPalabraDTO params) {

		PaisesDTO paisDestinoDTO = params.getPaisDestino();
		PaisesDTO paisOrigenDTO = params.getPaisOrigen();

		if (paisDestinoDTO == null || paisOrigenDTO == null) {
			return null;
		}

		String termino = params.getTermino();
		if (termino == null || termino.trim().isEmpty()) {
			return null;
		}

		String categoria = params.getCategoria();
		if (categoria == null || categoria.trim().isEmpty()) {
			return null;
		}

		String paisOrigen = params.getPaisOrigen().getNombre();
		String paisDestino = params.getPaisDestino().getNombre();

		if (paisOrigen == null || paisDestino == null || paisOrigen.trim().isEmpty() || paisDestino.trim().isEmpty()) {
			return null;
		}

		List<Sinonimos> listSinonimos = sinonimosRepository
				.findByPaisOrigenAndPaisDestinoAndTerminoAndCategoria(paisOrigen, paisDestino, termino, categoria);
		if (listSinonimos != null && !listSinonimos.isEmpty()) {
			List<SinonimosDTO> listSinonimosDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listSinonimos);
			return listSinonimosDTO;
		}
		
		return null;
	}

	/**
	 * realiza la busqueda por los dos paises
	 * 
	 * @param params
	 * @return
	 */
	public List<SinonimosDTO> buscarPorPaises(ParamsBusquedaPalabraDTO params) {
		PaisesDTO paisDestinoDTO = params.getPaisDestino();
		PaisesDTO paisOrigenDTO = params.getPaisOrigen();

		if (paisDestinoDTO == null || paisOrigenDTO == null) {
			return null;
		}

		String paisOrigen = params.getPaisOrigen().getNombre();
		String paisDestino = params.getPaisDestino().getNombre();

		if (paisOrigen == null || paisDestino == null || paisOrigen.trim().isEmpty() || paisDestino.trim().isEmpty()) {
			return null;
		}

		List<Sinonimos> listSinonimos = sinonimosRepository.findByPaisOrigenAndPaisDestino(paisOrigen, paisDestino);
		if (listSinonimos != null && !listSinonimos.isEmpty()) {
			List<SinonimosDTO> listSinonimosDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listSinonimos);
			return listSinonimosDTO;
		}
		return null;
	}

	private List<SinonimosDTO> buscarPalabraAproximada(String termino, List<SinonimosDTO> listSinonimosAll) {
		String numCoincidencia = env.getProperty("num_coincidencia_palabra");
		int numCoinc = 2;
		if (numCoincidencia != null && !numCoincidencia.trim().isEmpty()) {
			numCoinc = Integer.parseInt(numCoincidencia);
		}

		List<SinonimosDTO> listSinonimos = new ArrayList<>();
		for (SinonimosDTO sinonimos : listSinonimosAll) {
			int distance = LevenshteinDistance.computeLevenshteinDistance(termino, sinonimos.getTermino());
			if (distance <= numCoinc) {
				listSinonimos.add(sinonimos);
			}

			String[] sWords = sinonimos.getTermino().split(" ");
			if (sWords.length > 1) {
				for (String palabra : sWords) {
					distance = LevenshteinDistance.computeLevenshteinDistance(termino, palabra);
					if (distance <= numCoinc) {
						if (listSinonimos.contains(sinonimos)) {
							continue;
						}
						listSinonimos.add(sinonimos);
					}
				}
			}

			sWords = termino.split(" ");
			if (sWords.length > 1) {
				for (String palabra : sWords) {
					String[] cat = sinonimos.getTermino().split(" ");
					boolean validarPalabraAproximada = false;
					if (cat.length > 1) {
						validarPalabraAproximada = validarPalabraAproximada(sinonimos.getTermino(), palabra, numCoinc);
					}

					if (validarPalabraAproximada) {
						if (listSinonimos.contains(sinonimos)) {
							continue;
						}
						listSinonimos.add(sinonimos);
					}

					distance = LevenshteinDistance.computeLevenshteinDistance(palabra, sinonimos.getTermino());
					if (distance <= numCoinc) {
						if (listSinonimos.contains(sinonimos)) {
							continue;
						}
						listSinonimos.add(sinonimos);
					}
				}
			}

			String sTexto = sinonimos.getTermino();
			int encontro = sTexto.indexOf(termino);
			if (encontro == -1) {
				continue;
			}

			if (listSinonimos.contains(sinonimos)) {
				continue;
			}

			if (sTexto.length() > 1) {
				sTexto = sTexto.substring(sTexto.indexOf(termino), sTexto.length());
				while (sTexto.indexOf(termino) > -1) {
					sTexto = sTexto.substring(sTexto.indexOf(termino), sTexto.length());
					if (listSinonimos.contains(sinonimos)) {
						break;
					}
					listSinonimos.add(sinonimos);
				}
			}
		}

		return listSinonimos;

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
