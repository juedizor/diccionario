package co.com.diccionario.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.negocio.iface.GestionarBusquedaPalabrasIface;
import co.com.diccionario.rest.exception.CommonException;
import co.com.diccionario.rest.exception.GeneralErrorException;
import co.com.diccionario.rest.exception.NotFoundException;

@RestController
@RequestMapping("/palabras")
public class GestionarPalabrasService {

	@Autowired
	GestionarBusquedaPalabrasIface gestionarBusquedaPalabrasIface;

	@RequestMapping(method = RequestMethod.GET)
	public List<SinonimosDTO> getPalabras() throws CommonException {
		List<SinonimosDTO> listPalabras;
		try {
			listPalabras = gestionarBusquedaPalabrasIface.obtenerTodasLasPalabras();
		} catch (Exception e) {
			throw new GeneralErrorException("Error consultando palabras " + e.getMessage());
		}

		if (listPalabras == null || listPalabras.isEmpty()) {
			throw new NotFoundException("no hay palabras en el sistema");
		}

		return listPalabras;
	}

	@RequestMapping(value = "/busqueda", method = RequestMethod.POST)
	public List<SinonimosDTO> getPalabras(@RequestBody ParamsBusquedaPalabraDTO params) throws CommonException {
		List<SinonimosDTO> listPalabras;
		try {
			listPalabras = gestionarBusquedaPalabrasIface.obtenerTodasLasPalabras(params);
		} catch (Exception e) {
			throw new GeneralErrorException("Error consultando palabras " + e.getMessage());
		}

		if (listPalabras == null || listPalabras.isEmpty()) {
			throw new NotFoundException("no hay palabras en el sistema");
		}

		return listPalabras;
	}

}
