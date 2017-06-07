package co.com.diccionario.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.mapper.PalabraMapper;
import co.com.diccionario.mongodb.repository.iface.SinonimosRepository;
import co.com.diccionario.negocio.iface.GestionarBusquedaPalabrasIface;

@Service
public class GestionarBusquedaPalabrasImpl implements GestionarBusquedaPalabrasIface {

	@Autowired
	SinonimosRepository sinonimosRepository;

	@Override
	public List<SinonimosDTO> obtenerTodasLasPalabras() {
		List<Sinonimos> listPalabras = sinonimosRepository.findAll();
		if (listPalabras != null && !listPalabras.isEmpty()) {
			List<SinonimosDTO> listPalabraDTO = PalabraMapper.INSTANCE.palabrasToPalabraDTOs(listPalabras);
			return listPalabraDTO;
		}
		return null;
	}

	@Override
	public List<SinonimosDTO> obtenerTodasLasPalabras(ParamsBusquedaPalabraDTO params) {
		List<SinonimosDTO> listPalabraDTO = null;
		return listPalabraDTO;
	}

}
