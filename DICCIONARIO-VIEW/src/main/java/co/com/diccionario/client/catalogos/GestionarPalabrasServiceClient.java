package co.com.diccionario.client.catalogos;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import co.com.diccionario.dto.ParametrosRegistroTermino;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.utils.ParamsBundle;

public class GestionarPalabrasServiceClient {

	private static GestionarPalabrasServiceClient INSTANCE;
	private static String HOST_END_POINT;
	private static final String HOST = "host_end_point";
	private static final String PALABRAS = "palabras";
	private static final String BUSQUEDA = "busqueda";
	private static final String SINONIMO = "sinonimo";
	private static final String ORACION = "oracion";
	private static final String CALIFICACION = "calificacion";

	private GestionarPalabrasServiceClient() {
		// TODO Auto-generated constructor stub
	}

	public static GestionarPalabrasServiceClient getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new GestionarPalabrasServiceClient();
			ParamsBundle.getInstance().getEtiquetasParamsClient(ParamsBundle.SERVICE_CLIENT_DICCIONARIO);
			HOST_END_POINT = ParamsBundle.getInstance().getMapParamsServiceClientPatios().get(HOST);
		}
		return INSTANCE;
	}

	public List<SinonimosDTO> obtenerSinonimos(ParamsBusquedaPalabraDTO paramsBusqueda) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/" + BUSQUEDA;
		try {
			HttpEntity<ParamsBusquedaPalabraDTO> httpEntity = new HttpEntity<ParamsBusquedaPalabraDTO>(paramsBusqueda);
			ResponseEntity<List<SinonimosDTO>> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
					new ParameterizedTypeReference<List<SinonimosDTO>>() {
					});
			List<SinonimosDTO> listSinonimos = response.getBody();
			return listSinonimos;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
			}

			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return null;
			}
		}

		return null;
	}

	public List<SinonimosDTO> getSinonimoCategoriaPalabra(ParamsBusquedaPalabraDTO paramsBusqueda) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/" + BUSQUEDA + "/validarSinonimoPalabra";
		try {
			HttpEntity<ParamsBusquedaPalabraDTO> httpEntity = new HttpEntity<ParamsBusquedaPalabraDTO>(paramsBusqueda);
			ResponseEntity<List<SinonimosDTO>> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
					new ParameterizedTypeReference<List<SinonimosDTO>>() {
					});
			List<SinonimosDTO> listSinonimos = response.getBody();
			return listSinonimos;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
			}

			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return null;
			}
		}

		return null;
	}

	public SinonimosDTO agregarNuevoSinonimo(SinonimosDTO sinonimosDTO) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/sinonimo";
		try {
			HttpEntity<SinonimosDTO> httpEntity = new HttpEntity<>(sinonimosDTO);
			ResponseEntity<SinonimosDTO> response = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity,
					new ParameterizedTypeReference<SinonimosDTO>() {
					});
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
		}
	}

	public List<SinonimosDTO> obtenerSinonimosCategoriaPalabra(ParamsBusquedaPalabraDTO paramsBusqueda)
			throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/" + BUSQUEDA + "/categoriaTermino";
		try {
			HttpEntity<ParamsBusquedaPalabraDTO> httpEntity = new HttpEntity<ParamsBusquedaPalabraDTO>(paramsBusqueda);
			ResponseEntity<List<SinonimosDTO>> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
					new ParameterizedTypeReference<List<SinonimosDTO>>() {
					});
			List<SinonimosDTO> listSinonimos = response.getBody();
			return listSinonimos;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
			}

			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return null;
			}
		}

		return null;
	}

	public List<SinonimosDTO> obtenerSinonimosPalabra(ParamsBusquedaPalabraDTO paramsBusqueda) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/" + BUSQUEDA + "/termino";
		try {
			HttpEntity<ParamsBusquedaPalabraDTO> httpEntity = new HttpEntity<ParamsBusquedaPalabraDTO>(paramsBusqueda);
			ResponseEntity<List<SinonimosDTO>> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
					new ParameterizedTypeReference<List<SinonimosDTO>>() {
					});
			List<SinonimosDTO> listSinonimos = response.getBody();
			return listSinonimos;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
			}

			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return null;
			}
		}

		return null;
	}

	public boolean crearNuevoTermino(ParametrosRegistroTermino params) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/sinonimo";
		try {
			ResponseEntity<HttpStatus> response = restTemplate.postForEntity(uri, params, HttpStatus.class);
			HttpStatus httpStatus = response.getBody();
			if (httpStatus.equals(HttpStatus.OK)) {
				return true;
			}

			return false;
		} catch (HttpClientErrorException e) {
			throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
		}
	}

	public SinonimosDTO actualizarCalificacionSinonimos(SinonimosDTO sinonimosDTO) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/" + SINONIMO + "/" + CALIFICACION;
		try {
			HttpEntity<SinonimosDTO> httpEntity = new HttpEntity<>(sinonimosDTO);
			ResponseEntity<SinonimosDTO> response = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity,
					new ParameterizedTypeReference<SinonimosDTO>() {
					});
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
		}
	}

	public SinonimosDTO actualizarCalificacionOraciones(SinonimosDTO sinonimosDTO) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/" + SINONIMO + "/" + ORACION + "/" + CALIFICACION;
		try {
			HttpEntity<SinonimosDTO> httpEntity = new HttpEntity<>(sinonimosDTO);
			ResponseEntity<SinonimosDTO> response = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity,
					new ParameterizedTypeReference<SinonimosDTO>() {
					});
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
		}
	}

	public SinonimosDTO actualizarOraciones(SinonimosDTO sinonimosDTO) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + PALABRAS + "/" + SINONIMO + "/" + ORACION;
		try {
			HttpEntity<SinonimosDTO> httpEntity = new HttpEntity<>(sinonimosDTO);
			ResponseEntity<SinonimosDTO> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
					new ParameterizedTypeReference<SinonimosDTO>() {
					});
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new Exception(e.getResponseBodyAsString() + " " + e.getMessage());
		}
	}

}
