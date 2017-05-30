package co.com.diccionario.client.catalogos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.utils.ParamsBundle;

public class CatalogosServiceClient {

	private static CatalogosServiceClient INSTANCE;
	private static String HOST_END_POINT;
	private static final String HOST = "host_end_point";
	private static final String CATALOGOS = "catalogos";

	private CatalogosServiceClient() {
		// TODO Auto-generated constructor stub
	}

	public static CatalogosServiceClient getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new CatalogosServiceClient();
			ParamsBundle.getInstance().getEtiquetasParamsClient(ParamsBundle.SERVICE_CLIENT_DICCIONARIO);
			HOST_END_POINT = ParamsBundle.getInstance().getMapParamsServiceClientPatios().get(HOST);
		}
		return INSTANCE;
	}

	public List<PaisesDTO> getPaises() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + CATALOGOS + "/paises";
		try {
			ResponseEntity<List<PaisesDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<PaisesDTO>>() {
					});
			List<PaisesDTO> listPaisDTO = response.getBody();
			return listPaisDTO;
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

	public List<DepartamentoDTO> getDepartamentos(String idPais) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + CATALOGOS + "/departamentos/{idPais}";
		Map<String, String> params = new HashMap<>();
		params.put("idPais", "" + idPais);
		try {
			ResponseEntity<List<DepartamentoDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<DepartamentoDTO>>() {
					}, params);
			List<DepartamentoDTO> listDepartamentoDTO = response.getBody();
			return listDepartamentoDTO;
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

	public List<CiudadDTO> getCiudades(String idDepartamento) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uri = HOST_END_POINT + CATALOGOS + "/ciudades/{idDepartamento}";
		Map<String, String> params = new HashMap<>();
		params.put("idDepartamento", "" + idDepartamento);
		try {
			ResponseEntity<List<CiudadDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<CiudadDTO>>() {
					}, params);
			List<CiudadDTO> listCiudadDTO = response.getBody();
			return listCiudadDTO;
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

}
