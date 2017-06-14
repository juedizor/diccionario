package co.com.diccionario.negocio.impl;

import java.util.ArrayList;
import java.util.List;

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

		/**
		 * agrega el sinonimo para pais origen y destino
		 */
		Sinonimos sinonimosInicial = sinonimosRepository.save(sinonimos);

		/**
		 * cambiamos el pais de origen por el destino y hacemos el insert de
		 * manera contraria, pero tenemos que realizar la busqueda del id
		 */
		String paisOrigen = sinonimos.getPaisDestino();
		String paisDestino = sinonimos.getPaisOrigen();
		String categoria = sinonimos.getCategoria();
		List<String> valuesSinonimos = sinonimos.getSinonimos();
		String termino = "";
		if (valuesSinonimos != null && !valuesSinonimos.isEmpty()) {
			termino = valuesSinonimos.get(valuesSinonimos.size() - 1);
		}

		List<Sinonimos> listSinonimos = sinonimosRepository
				.findByPaisOrigenAndPaisDestinoAndTerminoAndCategoria(paisOrigen, paisDestino, termino, categoria);
		if (listSinonimos != null && !listSinonimos.isEmpty()) {
			sinonimos = listSinonimos.get(0);
			String sinonimo = sinonimosDTO.getTermino();
			List<String> listTerminoComoSinonimo = new ArrayList<>();
			listTerminoComoSinonimo.add(sinonimo);
			sinonimos.setTermino(termino);
			sinonimos.setSinonimos(listTerminoComoSinonimo);
		} else {
			sinonimos.setPaisDestino(paisDestino);
			sinonimos.setPaisOrigen(paisOrigen);
			String sinonimo = sinonimosDTO.getTermino();
			List<String> listTerminoComoSinonimo = new ArrayList<>();
			listTerminoComoSinonimo.add(sinonimo);
			valuesSinonimos = sinonimos.getSinonimos();
			sinonimos.setTermino(termino);
			sinonimos.set_id(null);
			sinonimos.setSinonimos(listTerminoComoSinonimo);
		}
		sinonimosRepository.save(sinonimos);

		SinonimosDTO sinonimosResult = SinonimosMapper.INSTANCE.sinonimosToSinonimoDTO(sinonimosInicial);
		return sinonimosResult;
	}

}
