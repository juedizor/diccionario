package co.com.diccionario.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.diccionario.dto.ParametrosRegistroTermino;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.negocio.iface.GestionarBusquedaPalabrasIface;
import co.com.diccionario.negocio.iface.GestionarRegistroPalabrasIface;
import co.com.diccionario.rest.exception.CommonException;
import co.com.diccionario.rest.exception.GeneralErrorException;
import co.com.diccionario.rest.exception.NotFoundException;

@RestController
@RequestMapping("/palabras")
public class GestionarPalabrasService {

	@Autowired
	GestionarBusquedaPalabrasIface gestionarBusquedaPalabrasIface;
	@Autowired
	GestionarRegistroPalabrasIface gestionarRegistroPalabrasIface;

	@RequestMapping(value = "/busqueda", method = RequestMethod.POST)
	public List<SinonimosDTO> getPalabrasTerminoCategoria(@RequestBody ParamsBusquedaPalabraDTO params)
			throws CommonException {
		List<SinonimosDTO> listPalabras;
		try {
			listPalabras = gestionarBusquedaPalabrasIface.obtenerPalabrasPorTerminoCategoria(params);
		} catch (Exception e) {
			throw new GeneralErrorException("Error consultando palabras " + e.getMessage());
		}

		if (listPalabras == null || listPalabras.isEmpty()) {
			throw new NotFoundException("no hay palabras en el sistema");
		}

		return listPalabras;
	}

	@RequestMapping(value = "/busqueda/validarSinonimoPalabra", method = RequestMethod.POST)
	public List<SinonimosDTO> validarSinonimoPorAgregar(@RequestBody ParamsBusquedaPalabraDTO params)
			throws CommonException {
		List<SinonimosDTO> listPalabras;
		try {
			listPalabras = gestionarBusquedaPalabrasIface.obtenerSinonimosPorSinonimo(params);
		} catch (Exception e) {
			throw new GeneralErrorException("Error consultando palabras " + e.getMessage());
		}

		if (listPalabras == null || listPalabras.isEmpty()) {
			throw new NotFoundException("no hay palabras en el sistema");
		}

		return listPalabras;

	}

	@RequestMapping(value = "/busqueda/categoriaTermino", method = RequestMethod.POST)
	public List<SinonimosDTO> buscarPorCategoriaTermino(@RequestBody ParamsBusquedaPalabraDTO params)
			throws CommonException {
		List<SinonimosDTO> listPalabras;
		try {
			listPalabras = gestionarBusquedaPalabrasIface.buscarPorCategoriaTermino(params);
		} catch (Exception e) {
			throw new GeneralErrorException("Error consultando palabras " + e.getMessage());
		}

		if (listPalabras == null || listPalabras.isEmpty()) {
			throw new NotFoundException("no hay palabras en el sistema");
		}

		return listPalabras;
	}

	@RequestMapping(value = "/busqueda/termino", method = RequestMethod.POST)
	public List<SinonimosDTO> buscarPorTermino(@RequestBody ParamsBusquedaPalabraDTO params) throws CommonException {
		List<SinonimosDTO> listPalabras;
		try {
			listPalabras = gestionarBusquedaPalabrasIface.buscarPorTermino(params);
		} catch (Exception e) {
			throw new GeneralErrorException("Error consultando palabras " + e.getMessage());
		}

		if (listPalabras == null || listPalabras.isEmpty()) {
			throw new NotFoundException("no hay palabras en el sistema");
		}

		return listPalabras;
	}

	@RequestMapping(value = "/sinonimo", method = RequestMethod.PUT)
	public SinonimosDTO actualizarSinonimos(@RequestBody SinonimosDTO sinonimosDTO) throws CommonException {
		try {
			SinonimosDTO sinonimoActualizado = gestionarRegistroPalabrasIface.actualizarSinonimos(sinonimosDTO);
			return sinonimoActualizado;
		} catch (Exception e) {
			throw new GeneralErrorException("Ocurrio error actualizando los sinonimos " + e.getMessage());
		}
	}

	@RequestMapping(value = "/sinonimo", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> registrarNuevoTermino(@RequestBody ParametrosRegistroTermino params)
			throws CommonException {
		try {
			gestionarRegistroPalabrasIface.registrarSinonimosPalabrasCompleta(params);
		} catch (Exception e) {
			throw new GeneralErrorException(
					"Error realizando el registro " + e.getMessage() + " " + e.getLocalizedMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping(value = "/sinonimo/calificacion", method = RequestMethod.PUT)
	public SinonimosDTO actualizarCalificacionSinonimos(@RequestBody SinonimosDTO sinonimosDTO) throws CommonException {
		try {
			return gestionarRegistroPalabrasIface.actualizarCalificacionSinonimos(sinonimosDTO);
		} catch (Exception e) {
			throw new GeneralErrorException(
					"Error realizando la actualizacion " + e.getMessage() + " " + e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/sinonimo/oracion/calificacion", method = RequestMethod.PUT)
	public SinonimosDTO actualizarCalificacionOracion(@RequestBody SinonimosDTO sinonimosDTO) throws CommonException {
		try {
			return gestionarRegistroPalabrasIface.actualizarCalificacionOraciones(sinonimosDTO);
		} catch (Exception e) {
			throw new GeneralErrorException(
					"Error realizando la actualizacion " + e.getMessage() + " " + e.getLocalizedMessage());
		}
	}

}
