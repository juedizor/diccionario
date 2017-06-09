package co.com.diccionario.negocio.cacheable.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Paises;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.mapper.PaisesMapper;
import co.com.diccionario.mongodb.repository.iface.PaisesRepository;
import co.com.diccionario.negocio.cacheable.iface.PaisesCaheableIface;

@Service
public class PaisesCacheableImpl implements PaisesCaheableIface {

	@Autowired
	PaisesRepository paisesRepository;

	@Override
	@Cacheable("paises")
	public List<PaisesDTO> obtenerPaises() {
		List<Paises> listPaises = paisesRepository.findAll();
		if (listPaises != null && !listPaises.isEmpty()) {
			List<PaisesDTO> listPaisesDTO = PaisesMapper.INSTANCE.paisesToPaisesDTOs(listPaises);
			return listPaisesDTO;
		}
		return null;
	}

}
