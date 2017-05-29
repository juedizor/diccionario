package co.com.diccionario.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Paises;
import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.mapper.PaisesMapper;
import co.com.diccionario.mongodb.iface.PaisesRepository;
import co.com.diccionario.negocio.iface.CatalogosIface;

@Service
public class CatalogosImpl implements CatalogosIface {

	@Autowired
	PaisesRepository paisesRepository;

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
	public List<DepartamentoDTO> obtenerDepartamentos(int idPais) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CiudadDTO> obtenerCiudades(int departamento) {
		// TODO Auto-generated method stub
		return null;
	}

}
