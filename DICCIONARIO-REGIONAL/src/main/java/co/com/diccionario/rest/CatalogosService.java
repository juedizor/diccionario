package co.com.diccionario.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.negocio.iface.CatalogosIface;
import co.com.diccionario.rest.exception.CommonException;
import co.com.diccionario.rest.exception.GeneralErrorException;
import co.com.diccionario.rest.exception.NotFoundException;

@RestController
@RequestMapping("/catalogos")
public class CatalogosService {

	@Autowired
	CatalogosIface catalogosIface;

	@RequestMapping(value = "/paises", method = RequestMethod.GET)
	public List<PaisesDTO> getPaises() throws CommonException {
		List<PaisesDTO> listPaises;
		try {
			listPaises = catalogosIface.obtenerPaises();
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		if (listPaises == null || listPaises.isEmpty()) {
			throw new NotFoundException("No hay datos en el sistema de paises");
		}

		return listPaises;

	}

}
