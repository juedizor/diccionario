package co.com.diccionario.rest;

import java.text.Normalizer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.negocio.iface.ConsultarCatalogosIface;
import co.com.diccionario.negocio.iface.RegistrarCatalogosIface;
import co.com.diccionario.rest.exception.CommonException;
import co.com.diccionario.rest.exception.GeneralErrorException;
import co.com.diccionario.rest.exception.NotFoundException;

@RestController
@RequestMapping("/catalogos")
public class CatalogosService {

	@Autowired
	ConsultarCatalogosIface consultarCatalogosIface;
	@Autowired
	RegistrarCatalogosIface registrarCatalogosIface;

	@RequestMapping(value = "/paises", method = RequestMethod.GET)
	public List<PaisesDTO> getPaises() throws CommonException {
		List<PaisesDTO> listPaises;
		try {
			listPaises = consultarCatalogosIface.obtenerPaises();
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		if (listPaises == null || listPaises.isEmpty()) {
			throw new NotFoundException("No hay datos en el sistema de paises");
		}

		return listPaises;

	}

	@RequestMapping(value = "/paises/{nombre}", method = RequestMethod.GET)
	public List<PaisesDTO> getPaises(@PathVariable(value = "nombre") String nombre) throws CommonException {
		List<PaisesDTO> listPaises;
		try {
			if (nombre == null || nombre.trim().isEmpty()) {
				throw new GeneralErrorException("parametros de consulta vacios");
			}
			nombre = Normalizer.normalize(nombre.trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			listPaises = consultarCatalogosIface.obtenerPaises(nombre.trim());
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		if (listPaises == null || listPaises.isEmpty()) {
			throw new NotFoundException("No existe el pais solicitado " + nombre);
		}

		return listPaises;

	}

	@RequestMapping(value = "/paises", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> crearPais(@RequestBody PaisesDTO paisesDTO) throws CommonException {
		if (paisesDTO == null) {
			throw new GeneralErrorException("Datos vacios");
		}
		try {
			registrarCatalogosIface.registraPaises(paisesDTO);
		} catch (Exception e) {
			throw new GeneralErrorException("Error registrando el pais " + e.getMessage());
		}

		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/departamentos/{idPais}", method = RequestMethod.GET)
	public List<DepartamentoDTO> getDepartamentos(@PathVariable(value = "idPais") int idPais) throws CommonException {
		List<DepartamentoDTO> listDepa;
		try {
			listDepa = consultarCatalogosIface.obtenerDepartamentos(idPais);
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		if (listDepa == null || listDepa.isEmpty()) {
			throw new NotFoundException("No hay datos en el sistema de departamentos");
		}

		return listDepa;
	}

	@RequestMapping(value = "/departamentos/{idPais}/{nombre}", method = RequestMethod.GET)
	public List<DepartamentoDTO> getDepartamento(@PathVariable(value = "idPais") int idPais,
			@PathVariable(value = "nombre") String nombre) throws CommonException {
		List<DepartamentoDTO> listDepa;
		try {
			if (idPais <= 0 || nombre == null || nombre.trim().isEmpty()) {
				throw new GeneralErrorException("parametros de consulta vacios");
			}
			nombre = Normalizer.normalize(nombre.trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			listDepa = consultarCatalogosIface.obtenerDepartamentos(idPais, nombre.trim());
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		if (listDepa == null || listDepa.isEmpty()) {
			throw new NotFoundException("El departamento solicitado no existe " + nombre);
		}

		return listDepa;
	}

	@RequestMapping(value = "/departamentos", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> crearDepartamento(@RequestBody DepartamentoDTO departamentoDTO)
			throws CommonException {
		try {
			if (departamentoDTO == null) {
				throw new GeneralErrorException("Datos vacios");
			}

			registrarCatalogosIface.registrarDepartamento(departamentoDTO);
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ciudades/{idDepartamento}", method = RequestMethod.GET)
	public List<CiudadDTO> getCiudad(@PathVariable(value = "idDepartamento") int idDepartamento)
			throws CommonException {
		List<CiudadDTO> listCiudadDTO;
		try {
			listCiudadDTO = consultarCatalogosIface.obtenerCiudades(idDepartamento);
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		if (listCiudadDTO == null || listCiudadDTO.isEmpty()) {
			throw new NotFoundException("No hay datos en el sistema de ciudades");
		}

		return listCiudadDTO;
	}

	@RequestMapping(value = "/ciudades/{idDepartamento}/{nombre}", method = RequestMethod.GET)
	public List<CiudadDTO> getCiudad(@PathVariable(value = "idDepartamento") int idDepartamento,
			@PathVariable(value = "nombre") String nombre) throws CommonException {
		List<CiudadDTO> listCiudadDTO;
		try {
			if (idDepartamento <= 0 || nombre == null || nombre.trim().isEmpty()) {
				throw new GeneralErrorException("parametros de consulta vacios");
			}

			listCiudadDTO = consultarCatalogosIface.obtenerCiudades(idDepartamento, nombre);
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		if (listCiudadDTO == null || listCiudadDTO.isEmpty()) {
			throw new NotFoundException("no existe la ciudad solicitada " + nombre);
		}

		return listCiudadDTO;
	}

	@RequestMapping(value = "/ciudades", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> getCiudad(@RequestBody CiudadDTO ciudadDTO) throws CommonException {
		try {
			if (ciudadDTO == null) {
				throw new GeneralErrorException("Datos vacios");
			}

			registrarCatalogosIface.registrarCiudad(ciudadDTO);
		} catch (Exception e) {
			throw new GeneralErrorException("Error desconocido " + e.getMessage());
		}

		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/categorias", method = RequestMethod.GET)
	public List<CategoriaDTO> getCategorias() throws CommonException {
		List<CategoriaDTO> listCat;
		try {
			listCat = consultarCatalogosIface.obtenerCategorias();
		} catch (Exception e) {
			throw new GeneralErrorException("Ocurrio error " + e.getMessage());
		}

		if (listCat == null || listCat.isEmpty()) {
			throw new NotFoundException("no hay datos de categorias");
		}

		return listCat;
	}

	@RequestMapping(value = "/categorias/{nombre}", method = RequestMethod.GET)
	public List<CategoriaDTO> getCategorias(@PathVariable(value = "nombre") String nombre) throws CommonException {
		List<CategoriaDTO> listCat;
		try {
			if (nombre == null || nombre.trim().isEmpty()) {
				throw new GeneralErrorException("el parametro nombre esta vacio");
			}
			nombre = Normalizer.normalize(nombre.trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			listCat = consultarCatalogosIface.obtenerCategorias(nombre);
		} catch (Exception e) {
			throw new GeneralErrorException("Ocurrio error " + e.getMessage());
		}

		if (listCat == null || listCat.isEmpty()) {
			throw new NotFoundException("no existe la categoria " + nombre + "");
		}

		return listCat;
	}

	@RequestMapping(value = "/categorias", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) throws CommonException {
		try {
			if (categoriaDTO == null) {
				throw new GeneralErrorException("Datos vacios");
			}
			registrarCatalogosIface.registrarCategoria(categoriaDTO);
		} catch (Exception e) {
			throw new GeneralErrorException("Ocurrio error registrando la categoria " + e.getMessage());
		}

		return ResponseEntity.ok(HttpStatus.CREATED);
	}

}
