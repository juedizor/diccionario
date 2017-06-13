package co.com.diccionario.negocio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.mapper.SinonimosMapper;
import co.com.diccionario.mongodb.repository.iface.SinonimosRepository;
import co.com.diccionario.negocio.iface.GestionarRegistroPalabrasIface;

@Service
public class GestionarRegistroPalabrasImpl implements GestionarRegistroPalabrasIface {

	@Autowired
	SinonimosRepository sinonimosRepository;

	@Override
	public SinonimosDTO actualizarSinonimos(SinonimosDTO sinonimosDTO) {
		Sinonimos sinonimos = SinonimosMapper.INSTANCE.sinonimoDTOToSinonimo(sinonimosDTO);
		sinonimos = sinonimosRepository.save(sinonimos);
		SinonimosDTO sinonimosResult = SinonimosMapper.INSTANCE.sinonimosToSinonimoDTO(sinonimos);
		return sinonimosResult;
	}

}
