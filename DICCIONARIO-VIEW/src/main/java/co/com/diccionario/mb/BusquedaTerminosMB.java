package co.com.diccionario.mb;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import co.com.diccionario.client.catalogos.CatalogosServiceClient;
import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.utils.ParamsBundle;
import co.com.diccionario.utils.Utils;

@ManagedBean(name = "busquedaMB")
@ViewScoped
public class BusquedaTerminosMB {

	private String idPais;
	private Map<String, String> mapPaises;

	private String idPaisDestino;
	private Map<String, String> mapPaisesDestino;

	private String idDepartamento;
	private Map<String, String> mapDepartamento;

	private String idDepartamentoDestino;
	private Map<String, String> mapDepartamentoDestino;

	private String idCiudad;
	private Map<String, String> mapCiudad;

	private String idCiudadDestino;
	private Map<String, String> mapCiudadDestino;

	public BusquedaTerminosMB() throws Exception {
		// TODO Auto-generated constructor stub
		ParamsBundle.getInstance().getEtiquetas(ParamsBundle.MSG);
	}

	@PostConstruct
	public void cargarPaises() {
		obtenerPaises();
	}

	public void obtenerPaises() {
		List<PaisesDTO> listPaisesDTO;
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		try {
			listPaisesDTO = CatalogosServiceClient.getInstance().getPaises();
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));

			return;
		}

		if (listPaisesDTO == null || listPaisesDTO.isEmpty()) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_no_paises"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
			return;
		}

		mapPaises = new LinkedHashMap<>();
		mapPaisesDestino = new LinkedHashMap<>();
		for (PaisesDTO paisesDTO : listPaisesDTO) {
			mapPaises.put(paisesDTO.getNombre(), "" + paisesDTO.getId());
			mapPaisesDestino.put(paisesDTO.getNombre(), "" + paisesDTO.getId());
		}

	}

	public void cargarDepartamentosDestino() {
		if (idPaisDestino == null || idPaisDestino.trim().isEmpty()) {
			mapDepartamentoDestino = new LinkedHashMap<>();
			idDepartamentoDestino = "-1";
			return;
		}
		List<DepartamentoDTO> listDepartamentoDTO;
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		try {
			listDepartamentoDTO = CatalogosServiceClient.getInstance().getDepartamentos(idPaisDestino);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			mapDepartamentoDestino = new LinkedHashMap<>();
			return;
		}

		if (listDepartamentoDTO == null || listDepartamentoDTO.isEmpty()) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_no_departamentos"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
			mapDepartamentoDestino = new LinkedHashMap<>();
			return;
		}

		mapDepartamentoDestino = new LinkedHashMap<>();
		for (DepartamentoDTO departamentoDTO : listDepartamentoDTO) {
			mapDepartamentoDestino.put(departamentoDTO.getNombre(), "" + departamentoDTO.getId());
		}
	}

	public void cargarDepartamentosOrigen() {
		if (idPais == null || idPais.trim().isEmpty()) {
			mapDepartamento = new LinkedHashMap<>();
			idDepartamento = "-1";
			return;
		}
		List<DepartamentoDTO> listDepartamentoDTO;
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		try {
			listDepartamentoDTO = CatalogosServiceClient.getInstance().getDepartamentos(idPais);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			mapDepartamento = new LinkedHashMap<>();
			return;
		}

		if (listDepartamentoDTO == null || listDepartamentoDTO.isEmpty()) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_no_departamentos"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
			mapDepartamento = new LinkedHashMap<>();
			return;
		}

		mapDepartamento = new LinkedHashMap<>();
		for (DepartamentoDTO departamentoDTO : listDepartamentoDTO) {
			mapDepartamento.put(departamentoDTO.getNombre(), "" + departamentoDTO.getId());
		}
	}

	public void cargarCiudadOrigen() {
		if (idDepartamento == null || idDepartamento.trim().isEmpty()) {
			mapCiudad = new LinkedHashMap<>();
			idCiudad = "-1";
			return;
		}
		List<CiudadDTO> listCiudadDTO;
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		try {
			listCiudadDTO = CatalogosServiceClient.getInstance().getCiudades(idDepartamento);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			mapCiudad = new LinkedHashMap<>();
			return;
		}

		if (listCiudadDTO == null || listCiudadDTO.isEmpty()) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_no_ciudad"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
			mapCiudad = new LinkedHashMap<>();
			return;
		}

		mapCiudad = new LinkedHashMap<>();
		for (CiudadDTO ciudadDTO : listCiudadDTO) {
			mapCiudad.put(ciudadDTO.getNombre(), "" + ciudadDTO.getId());
		}
	}

	public void cargarCiudadDestino() {
		if (idDepartamentoDestino == null || idDepartamentoDestino.trim().isEmpty()) {
			mapCiudadDestino = new LinkedHashMap<>();
			idCiudadDestino = "-1";
			return;
		}
		List<CiudadDTO> listCiudadDTO;
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		try {
			listCiudadDTO = CatalogosServiceClient.getInstance().getCiudades(idDepartamentoDestino);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			mapCiudadDestino = new LinkedHashMap<>();
			return;
		}

		if (listCiudadDTO == null || listCiudadDTO.isEmpty()) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_no_ciudad"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
			mapCiudadDestino = new LinkedHashMap<>();
			return;
		}

		mapCiudadDestino = new LinkedHashMap<>();
		for (CiudadDTO ciudadDTO : listCiudadDTO) {
			mapCiudadDestino.put(ciudadDTO.getNombre(), "" + ciudadDTO.getId());
		}
	}

	public void addNuevoPais() {

	}

	/**
	 * @return the mapPaises
	 */
	public Map<String, String> getMapPaises() {
		return mapPaises;
	}

	/**
	 * @param mapPaises
	 *            the mapPaises to set
	 */
	public void setMapPaises(Map<String, String> mapPaises) {
		this.mapPaises = mapPaises;
	}

	/**
	 * @return the idPais
	 */
	public String getIdPais() {
		return idPais;
	}

	/**
	 * @param idPais
	 *            the idPais to set
	 */
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	/**
	 * @return the idPaisDestino
	 */
	public String getIdPaisDestino() {
		return idPaisDestino;
	}

	/**
	 * @param idPaisDestino
	 *            the idPaisDestino to set
	 */
	public void setIdPaisDestino(String idPaisDestino) {
		this.idPaisDestino = idPaisDestino;
	}

	/**
	 * @return the mapPaisesDestino
	 */
	public Map<String, String> getMapPaisesDestino() {
		return mapPaisesDestino;
	}

	/**
	 * @param mapPaisesDestino
	 *            the mapPaisesDestino to set
	 */
	public void setMapPaisesDestino(Map<String, String> mapPaisesDestino) {
		this.mapPaisesDestino = mapPaisesDestino;
	}

	/**
	 * @return the idDepartamento
	 */
	public String getIdDepartamento() {
		return idDepartamento;
	}

	/**
	 * @param idDepartamento
	 *            the idDepartamento to set
	 */
	public void setIdDepartamento(String idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	/**
	 * @return the mapDepartamento
	 */
	public Map<String, String> getMapDepartamento() {
		return mapDepartamento;
	}

	/**
	 * @param mapDepartamento
	 *            the mapDepartamento to set
	 */
	public void setMapDepartamento(Map<String, String> mapDepartamento) {
		this.mapDepartamento = mapDepartamento;
	}

	/**
	 * @return the idDepartamentoDestino
	 */
	public String getIdDepartamentoDestino() {
		return idDepartamentoDestino;
	}

	/**
	 * @param idDepartamentoDestino
	 *            the idDepartamentoDestino to set
	 */
	public void setIdDepartamentoDestino(String idDepartamentoDestino) {
		this.idDepartamentoDestino = idDepartamentoDestino;
	}

	/**
	 * @return the mapDepartamentoDestino
	 */
	public Map<String, String> getMapDepartamentoDestino() {
		return mapDepartamentoDestino;
	}

	/**
	 * @param mapDepartamentoDestino
	 *            the mapDepartamentoDestino to set
	 */
	public void setMapDepartamentoDestino(Map<String, String> mapDepartamentoDestino) {
		this.mapDepartamentoDestino = mapDepartamentoDestino;
	}

	/**
	 * @return the idCiudad
	 */
	public String getIdCiudad() {
		return idCiudad;
	}

	/**
	 * @param idCiudad
	 *            the idCiudad to set
	 */
	public void setIdCiudad(String idCiudad) {
		this.idCiudad = idCiudad;
	}

	/**
	 * @return the mapCiudad
	 */
	public Map<String, String> getMapCiudad() {
		return mapCiudad;
	}

	/**
	 * @param mapCiudad
	 *            the mapCiudad to set
	 */
	public void setMapCiudad(Map<String, String> mapCiudad) {
		this.mapCiudad = mapCiudad;
	}

	/**
	 * @return the idCiudadDestino
	 */
	public String getIdCiudadDestino() {
		return idCiudadDestino;
	}

	/**
	 * @param idCiudadDestino
	 *            the idCiudadDestino to set
	 */
	public void setIdCiudadDestino(String idCiudadDestino) {
		this.idCiudadDestino = idCiudadDestino;
	}

	/**
	 * @return the mapCiudadDestino
	 */
	public Map<String, String> getMapCiudadDestino() {
		return mapCiudadDestino;
	}

	/**
	 * @param mapCiudadDestino
	 *            the mapCiudadDestino to set
	 */
	public void setMapCiudadDestino(Map<String, String> mapCiudadDestino) {
		this.mapCiudadDestino = mapCiudadDestino;
	}

}
