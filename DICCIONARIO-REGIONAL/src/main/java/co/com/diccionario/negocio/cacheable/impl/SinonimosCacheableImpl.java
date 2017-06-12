package co.com.diccionario.negocio.cacheable.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.mapper.SinonimosMapper;
import co.com.diccionario.mongodb.repository.iface.SinonimosRepository;
import co.com.diccionario.negocio.cacheable.iface.SinonimosCachableIface;

@Service
public class SinonimosCacheableImpl implements SinonimosCachableIface {

	@Autowired
	SinonimosRepository sinonimosRepository;

	@Override
	@Cacheable("sinonimos")
	public List<SinonimosDTO> obtenerTodasLasPalabras() {
		List<Sinonimos> listPalabras = sinonimosRepository.findAll();
		if (listPalabras != null && !listPalabras.isEmpty()) {
			List<SinonimosDTO> listPalabraDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listPalabras);
			return listPalabraDTO;
		}
		return null;
	}

}
