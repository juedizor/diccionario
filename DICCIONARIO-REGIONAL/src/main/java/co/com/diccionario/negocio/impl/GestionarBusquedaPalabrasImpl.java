package co.com.diccionario.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Departamento;
import co.com.diccionario.document.Paises;
import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.mapper.DepartamentoMapper;
import co.com.diccionario.mapper.PaisesMapper;
import co.com.diccionario.mapper.SinonimosMapper;
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
			List<SinonimosDTO> listPalabraDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listPalabras);
			return listPalabraDTO;
		}
		return null;
	}
	
	@Override
	public List<SinonimosDTO> obtenerTodasLasPalabras(ParamsBusquedaPalabraDTO params) {
		Paises paisesDestino = PaisesMapper.INSTANCE.paisDTOToPaises(params.getPaisDestino());
		Paises paisesOrigen = PaisesMapper.INSTANCE.paisDTOToPaises(params.getPaisOrigen());
		DepartamentoDTO departamentoOrigenDTO = params.getDepartamentoOrigen();
		Departamento departamentoOrigen;
		if (departamentoOrigenDTO != null) {
			departamentoOrigen = DepartamentoMapper.INSTANCE.departamentoDTOToDepartamento(departamentoOrigenDTO);
		}

		DepartamentoDTO departamentoDestinoDTO = params.getDepartamentoDestino();
		Departamento departamentoDestino;
		if (departamentoDestinoDTO != null) {
			departamentoDestino = DepartamentoMapper.INSTANCE.departamentoDTOToDepartamento(departamentoDestinoDTO);
		}

		String termino = params.getTermino();
		String categoria = params.getCategoria();
		
		List<Sinonimos> listSinonimos = sinonimosRepository.findByPaisOrigenAndPaisDestino(paisesOrigen.getNombre(), paisesDestino.getNombre());
		if(listSinonimos != null && !listSinonimos.isEmpty()){
			List<SinonimosDTO> listSinonimosDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimosDTOs(listSinonimos);
			return listSinonimosDTO;
		}
		return null;
	}

}
