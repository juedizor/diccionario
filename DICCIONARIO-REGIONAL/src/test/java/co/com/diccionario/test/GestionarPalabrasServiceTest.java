package co.com.diccionario.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GestionarPalabrasServiceTest {

	@Autowired
	private WebApplicationContext context;

	@LocalServerPort
	private int port;

	private MockMvc mockMvc;
	private RestTemplate restTemplate = new RestTemplate();

	@Before
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getPalabrasTerminoCategoriaTest() {
		ParamsBusquedaPalabraDTO params = new ParamsBusquedaPalabraDTO();
		PaisesDTO paisOrigen = new PaisesDTO();
		PaisesDTO paisDestino = new PaisesDTO();
		paisOrigen.setNombre("COLOMBIA");
		paisDestino.setNombre("ARGENTINA");
		params.setPaisOrigen(paisOrigen);
		params.setPaisDestino(paisDestino);
		params.setTermino("BUZO");
		String uri = "http://localhost:" + port + "/palabras/busqueda/validarSinonimoPalabra";
		try {
			HttpEntity<ParamsBusquedaPalabraDTO> httpEntity = new HttpEntity<ParamsBusquedaPalabraDTO>(params);
			ResponseEntity<List<SinonimosDTO>> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
					new ParameterizedTypeReference<List<SinonimosDTO>>() {
					});
			List<SinonimosDTO> listSinonimos = response.getBody();
			assertTrue(listSinonimos != null && !listSinonimos.isEmpty());
		} catch (HttpClientErrorException e) {
			assertTrue(e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)
					|| e.getStatusCode().equals(HttpStatus.NOT_FOUND));
		}

	}

	@Test
	public void getPalabrasTerminoCategoriaTestMockMVC() throws Exception {
		ParamsBusquedaPalabraDTO params = new ParamsBusquedaPalabraDTO();
		PaisesDTO paisOrigen = new PaisesDTO();
		PaisesDTO paisDestino = new PaisesDTO();
		paisOrigen.setNombre("COLOMBIA");
		paisDestino.setNombre("ARGENTINA");
		params.setPaisOrigen(paisOrigen);
		params.setPaisDestino(paisDestino);
		params.setTermino("BUZO");
		String uri = "http://localhost:" + port + "/palabras/busqueda/validarSinonimoPalabra";
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(params);
		mockMvc.perform(MockMvcRequestBuilders.post("/palabras/busqueda/validarSinonimoPalabra")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInString))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
