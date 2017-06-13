package co.com.diccionario.mb;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import co.com.diccionario.client.catalogos.CatalogosServiceClient;
import co.com.diccionario.client.catalogos.GestionarPalabrasServiceClient;
import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.utils.ParamsBundle;
import co.com.diccionario.utils.Utils;

@ManagedBean(name = "busquedaMB")
@SessionScoped
public class BusquedaTerminosMB {

	private String idPais;
	private Map<String, String> mapPaises;

	private String idPaisDestino;
	private Map<String, String> mapPaisesDestino;

	private String idCategoria;
	private Map<String, String> mapCategorias;

	private String pais;

	private String palabra;

	private boolean isMostrarDestino;
	private boolean isMostrarCategoria;
	private boolean isMostrarOrigen = true;
	private boolean isMostrarBtnContinuarDestino = true;
	private boolean isMostrarBotonesDestino;
	private boolean isMostrarBotonesCategoria;
	private boolean isMostrarResultado;
	private String respuestaResultados;
	private boolean isMostrarBanderas;
	private String nombreBanderaPaisOrigen;
	private String nombreBanderaPaisDestino;

	private String mensajeResultado;
	private boolean mostrarMensajeResultado;

	private String categoria;
	private String posibleCategoria;
	private List<CategoriaDTO> listPosiblesCategorias;
	private String mensajeCategoriaAproximada;

	private List<SinonimosDTO> listResultadosBusquedaSinonimos;

	public BusquedaTerminosMB() throws Exception {
		// TODO Auto-generated constructor stub
		ParamsBundle.getInstance().getEtiquetas(ParamsBundle.MSG);
	}

	@PostConstruct
	public void cargarPaises() {
		obtenerPaises();
		obtenerCategorias();
	}

	public void buscarSinonimos() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (palabra == null || palabra.trim().isEmpty()) {
			requestContext.update("msg");
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_digite_palabra"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		ParamsBusquedaPalabraDTO params = new ParamsBusquedaPalabraDTO();

		PaisesDTO paisesOrigenDTO = new PaisesDTO();
		String valueMap = Utils.foundValueMap(mapPaises, idPais);
		paisesOrigenDTO.setNombre(valueMap);
		params.setPaisOrigen(paisesOrigenDTO);

		PaisesDTO paisesDestinoDTO = new PaisesDTO();
		valueMap = Utils.foundValueMap(mapPaisesDestino, idPaisDestino);
		paisesDestinoDTO.setNombre(valueMap);
		params.setPaisDestino(paisesDestinoDTO);

		valueMap = Utils.foundValueMap(mapCategorias, idCategoria);
		params.setCategoria(valueMap);

		params.setTermino(palabra);

		try {
			listResultadosBusquedaSinonimos = GestionarPalabrasServiceClient.getInstance().obtenerSinonimos(params);
		} catch (Exception e) {
			requestContext.update("msg");
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		if (listResultadosBusquedaSinonimos != null && !listResultadosBusquedaSinonimos.isEmpty()) {
			int i = 0;
			for (SinonimosDTO sinonimosDTO : listResultadosBusquedaSinonimos) {
				String[] oraciones = sinonimosDTO.getOraciones();
				if (oraciones == null || oraciones.length <= 0) {
					oraciones = new String[1];
					oraciones[0] = "No hay ejemplos";
					listResultadosBusquedaSinonimos.get(i).setOraciones(oraciones);
				}
				i++;
			}
			mensajeResultado = "Su consulta a arrojado los siguientes resultados";
			requestContext.update("busqueda:fieldMsgResultado");
			requestContext.update("busqueda:fieldPnlResultadosPalabras");
			mostrarMensajeResultado = true;
			isMostrarResultado = true;
		} else {
			mensajeResultado = "No hay resultados, con los filtros seleccionados";
			requestContext.update("busqueda:fieldMsgResultado");
			requestContext.update("busqueda:fieldPnlResultadosPalabras");
			mostrarMensajeResultado = true;
			isMostrarResultado = false;
		}

	}

	public void regresarPanelPaisDestino() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update("busqueda:fieldPnlUbicacion");
		requestContext.update("busqueda:btnContinuarDestino");
		requestContext.update("busqueda:btnsDestino");
		requestContext.update("busqueda:btnsCategoria");
		requestContext.update("busqueda:fieldPnlTerminos");
		requestContext.update("busqueda:fieldPnlResultadosPalabras");
		requestContext.update("busqueda:filtroSeleccionado");
		requestContext.update("busqueda:fieldMsgResultado");
		requestContext.update("busqueda:fieldPnlResultadosPalabras");
		mostrarMensajeResultado = false;
		isMostrarCategoria = false;
		isMostrarBotonesCategoria = false;
		isMostrarResultado = false;
		isMostrarBanderas = false;
		isMostrarDestino = true;
		isMostrarBotonesDestino = true;
		
	}

	public void continuarPanelCategoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (idPaisDestino == null || idPaisDestino.trim().isEmpty()) {
			requestContext.update("msg");
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_seleccione_pais"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		String nombrePaisOrigen = Utils.foundValueMap(mapPaises, idPais);
		String nombrePaisDestino = Utils.foundValueMap(mapPaisesDestino, idPaisDestino);
		nombreBanderaPaisOrigen = nombrePaisOrigen.toLowerCase() + ".png";
		nombreBanderaPaisDestino = nombrePaisDestino.toLowerCase() + ".png";
		
		
		requestContext.update("busqueda:fieldPnlUbicacion");
		requestContext.update("busqueda:btnContinuarDestino");
		requestContext.update("busqueda:btnsDestino");
		requestContext.update("busqueda:btnsCategoria");
		requestContext.update("busqueda:fieldPnlTerminos");
		requestContext.update("busqueda:filtroSeleccionado");
		isMostrarDestino = false;
		isMostrarBotonesDestino = false;
		isMostrarCategoria = true;
		isMostrarBotonesCategoria = true;
		isMostrarBanderas = true;
	}

	public void regresarPanelPaisOrigen() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update("busqueda:fieldPnlPais");
		requestContext.update("busqueda:fieldPnlUbicacion");
		requestContext.update("busqueda:btnContinuarDestino");
		requestContext.update("busqueda:btnsDestino");
		isMostrarDestino = false;
		isMostrarBotonesDestino = false;
		isMostrarOrigen = true;
		isMostrarBtnContinuarDestino = true;
	}

	public void continuarPanelPaisOrigen() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (idPais == null || idPais.trim().isEmpty()) {
			requestContext.update("msg");
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_seleccione_pais"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}
		requestContext.update("busqueda:fieldPnlPais");
		requestContext.update("busqueda:fieldPnlUbicacion");
		requestContext.update("busqueda:btnContinuarDestino");
		requestContext.update("busqueda:btnsDestino");
		isMostrarDestino = true;
		isMostrarOrigen = false;
		isMostrarBtnContinuarDestino = false;
		isMostrarBotonesDestino = true;

	}

	public void obtenerCategorias() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		List<CategoriaDTO> listCategoriaDTO;
		try {
			listCategoriaDTO = CatalogosServiceClient.getInstance().getCategorias();
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));

			return;
		}

		if (listCategoriaDTO == null || listCategoriaDTO.isEmpty()) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_no_categoria"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
			return;
		}

		mapCategorias = new LinkedHashMap<>();
		for (CategoriaDTO categoriaDTO : listCategoriaDTO) {
			mapCategorias.put(categoriaDTO.getNombre(), "" + categoriaDTO.getId());
		}
	}

	public void obtenerPaises() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		List<PaisesDTO> listPaisesDTO;
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

	public void cerrarDlgPais() {
		pais = "";
	}

	

	public void paisMayuscula() {
		if (pais != null && !pais.trim().isEmpty()) {
			pais = pais.trim().replaceAll(" +", " ");
			pais = pais.toUpperCase();
		}
	}

	public void palabraMayuscula() {
		if (palabra != null && !palabra.trim().isEmpty()) {
			palabra = palabra.trim().replaceAll(" +", " ");
			palabra = palabra.toUpperCase();
		}
	}

	public void categoriaMayuscula() {
		if (categoria != null && !categoria.trim().isEmpty()) {
			categoria = categoria.trim().replaceAll(" +", " ");
			categoria = categoria.toUpperCase();
		}
	}

	public void seleccionarCategoriaAproximada() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (posibleCategoria == null || posibleCategoria.trim().isEmpty()) {
			requestContext.update("msg");
			String msg = "Debe seleccionar la categoria";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		requestContext.update("busqueda:cmbCategoria");
		requestContext.update("formAddCategoria");
		requestContext.update("formAddCategoriaAproximadas");
		idCategoria = posibleCategoria;
		posibleCategoria = "";
		categoria = "";
		requestContext.execute("PF('dlgCategoriaAproximadas').hide();");
		requestContext.execute("PF('dlgCategoria').hide();");
	}

	public void addNuevaCategoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (categoria == null || categoria.trim().isEmpty()) {
			String msg = "Debe digitar la categoria";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		List<CategoriaDTO> listCategoriaDTO;
		try {
			listCategoriaDTO = CatalogosServiceClient.getInstance().getCategorias(categoria);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			return;
		}

		/*
		 * realiza la verificacion de nombre exacto
		 */
		if (listCategoriaDTO != null && !listCategoriaDTO.isEmpty()) {
			String msg = "Ya existe una categoria con el nombre " + categoria + "";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			setCategoria("");
			return;
		}

		/*
		 * realiza la verificacion de nombre aproximados
		 */
		try {
			listPosiblesCategorias = CatalogosServiceClient.getInstance().getCategoriasAproximados(categoria);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			return;
		}

		if (listPosiblesCategorias != null && !listPosiblesCategorias.isEmpty()) {
			/*
			 * en este punto ya tenemos resultado de categorias aproximadas a la
			 * digitada
			 */
			mensajeCategoriaAproximada = "La categoria " + categoria
					+ " que quieres agregar podria encontrarse en alguna de las siguientes, "
					+ "\n ¿Podrias seleccionar una de estas?";
			requestContext.update("formAddCategoriaAproximadas");
			requestContext.execute("PF('dlgCategoriaAproximadas').show();");
			return;
		}

		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setNombre(categoria);
		try {
			boolean creado = CatalogosServiceClient.getInstance().crearCategoria(categoriaDTO);
			if (creado) {
				obtenerCategorias();
				String mensaje = "La categoria con nombre " + categoria + " ha sido creada";
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null, mensaje,
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
				setCategoria("");
			}
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			return;
		}
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
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the idCategoria
	 */
	public String getIdCategoria() {
		return idCategoria;
	}

	/**
	 * @param idCategoria
	 *            the idCategoria to set
	 */
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * @return the mapCategorias
	 */
	public Map<String, String> getMapCategorias() {
		return mapCategorias;
	}

	/**
	 * @param mapCategorias
	 *            the mapCategorias to set
	 */
	public void setMapCategorias(Map<String, String> mapCategorias) {
		this.mapCategorias = mapCategorias;
	}

	/**
	 * @return the palabra
	 */
	public String getPalabra() {
		return palabra;
	}

	/**
	 * @param palabra
	 *            the palabra to set
	 */
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	/**
	 * @return the isMostrarDestino
	 */
	public boolean isMostrarDestino() {
		return isMostrarDestino;
	}

	/**
	 * @param isMostrarDestino
	 *            the isMostrarDestino to set
	 */
	public void setMostrarDestino(boolean isMostrarDestino) {
		this.isMostrarDestino = isMostrarDestino;
	}

	/**
	 * @return the isMostrarCategoria
	 */
	public boolean isMostrarCategoria() {
		return isMostrarCategoria;
	}

	/**
	 * @param isMostrarCategoria
	 *            the isMostrarCategoria to set
	 */
	public void setMostrarCategoria(boolean isMostrarCategoria) {
		this.isMostrarCategoria = isMostrarCategoria;
	}

	/**
	 * @return the isMostrarOrigen
	 */
	public boolean isMostrarOrigen() {
		return isMostrarOrigen;
	}

	/**
	 * @param isMostrarOrigen
	 *            the isMostrarOrigen to set
	 */
	public void setMostrarOrigen(boolean isMostrarOrigen) {
		this.isMostrarOrigen = isMostrarOrigen;
	}

	/**
	 * @return the isMostrarBtnContinuarDestino
	 */
	public boolean isMostrarBtnContinuarDestino() {
		return isMostrarBtnContinuarDestino;
	}

	/**
	 * @param isMostrarBtnContinuarDestino
	 *            the isMostrarBtnContinuarDestino to set
	 */
	public void setMostrarBtnContinuarDestino(boolean isMostrarBtnContinuarDestino) {
		this.isMostrarBtnContinuarDestino = isMostrarBtnContinuarDestino;
	}

	/**
	 * @return the isMostrarBotonesDestino
	 */
	public boolean isMostrarBotonesDestino() {
		return isMostrarBotonesDestino;
	}

	/**
	 * @param isMostrarBotonesDestino
	 *            the isMostrarBotonesDestino to set
	 */
	public void setMostrarBotonesDestino(boolean isMostrarBotonesDestino) {
		this.isMostrarBotonesDestino = isMostrarBotonesDestino;
	}

	/**
	 * @return the isMostrarBotonesCategoria
	 */
	public boolean isMostrarBotonesCategoria() {
		return isMostrarBotonesCategoria;
	}

	/**
	 * @param isMostrarBotonesCategoria
	 *            the isMostrarBotonesCategoria to set
	 */
	public void setMostrarBotonesCategoria(boolean isMostrarBotonesCategoria) {
		this.isMostrarBotonesCategoria = isMostrarBotonesCategoria;
	}

	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the posibleCategoria
	 */
	public String getPosibleCategoria() {
		return posibleCategoria;
	}

	/**
	 * @param posibleCategoria
	 *            the posibleCategoria to set
	 */
	public void setPosibleCategoria(String posibleCategoria) {
		this.posibleCategoria = posibleCategoria;
	}

	/**
	 * @return the listPosiblesCategorias
	 */
	public List<CategoriaDTO> getListPosiblesCategorias() {
		return listPosiblesCategorias;
	}

	/**
	 * @param listPosiblesCategorias
	 *            the listPosiblesCategorias to set
	 */
	public void setListPosiblesCategorias(List<CategoriaDTO> listPosiblesCategorias) {
		this.listPosiblesCategorias = listPosiblesCategorias;
	}

	/**
	 * @return the mensajeCategoriaAproximada
	 */
	public String getMensajeCategoriaAproximada() {
		return mensajeCategoriaAproximada;
	}

	/**
	 * @param mensajeCategoriaAproximada
	 *            the mensajeCategoriaAproximada to set
	 */
	public void setMensajeCategoriaAproximada(String mensajeCategoriaAproximada) {
		this.mensajeCategoriaAproximada = mensajeCategoriaAproximada;
	}

	/**
	 * @return the isMostrarResultado
	 */
	public boolean isMostrarResultado() {
		return isMostrarResultado;
	}

	/**
	 * @param isMostrarResultado
	 *            the isMostrarResultado to set
	 */
	public void setMostrarResultado(boolean isMostrarResultado) {
		this.isMostrarResultado = isMostrarResultado;
	}

	/**
	 * @return the listResultadosBusquedaSinonimos
	 */
	public List<SinonimosDTO> getListResultadosBusquedaSinonimos() {
		return listResultadosBusquedaSinonimos;
	}

	/**
	 * @param listResultadosBusquedaSinonimos
	 *            the listResultadosBusquedaSinonimos to set
	 */
	public void setListResultadosBusquedaSinonimos(List<SinonimosDTO> listResultadosBusquedaSinonimos) {
		this.listResultadosBusquedaSinonimos = listResultadosBusquedaSinonimos;
	}

	/**
	 * @return the respuestaResultados
	 */
	public String getRespuestaResultados() {
		return respuestaResultados;
	}

	/**
	 * @param respuestaResultados
	 *            the respuestaResultados to set
	 */
	public void setRespuestaResultados(String respuestaResultados) {
		this.respuestaResultados = respuestaResultados;
	}

	/**
	 * @return the nombreBanderaPaisOrigen
	 */
	public String getNombreBanderaPaisOrigen() {
		return nombreBanderaPaisOrigen;
	}

	/**
	 * @param nombreBanderaPaisOrigen
	 *            the nombreBanderaPaisOrigen to set
	 */
	public void setNombreBanderaPaisOrigen(String nombreBanderaPaisOrigen) {
		this.nombreBanderaPaisOrigen = nombreBanderaPaisOrigen;
	}

	/**
	 * @return the nombreBanderaPaisDestino
	 */
	public String getNombreBanderaPaisDestino() {
		return nombreBanderaPaisDestino;
	}

	/**
	 * @param nombreBanderaPaisDestino
	 *            the nombreBanderaPaisDestino to set
	 */
	public void setNombreBanderaPaisDestino(String nombreBanderaPaisDestino) {
		this.nombreBanderaPaisDestino = nombreBanderaPaisDestino;
	}

	/**
	 * @return the isMostrarBanderas
	 */
	public boolean isMostrarBanderas() {
		return isMostrarBanderas;
	}

	/**
	 * @param isMostrarBanderas
	 *            the isMostrarBanderas to set
	 */
	public void setMostrarBanderas(boolean isMostrarBanderas) {
		this.isMostrarBanderas = isMostrarBanderas;
	}

	/**
	 * @return the mensajeResultado
	 */
	public String getMensajeResultado() {
		return mensajeResultado;
	}

	/**
	 * @param mensajeResultado
	 *            the mensajeResultado to set
	 */
	public void setMensajeResultado(String mensajeResultado) {
		this.mensajeResultado = mensajeResultado;
	}

	/**
	 * @return the mostrarMensajeResultado
	 */
	public boolean isMostrarMensajeResultado() {
		return mostrarMensajeResultado;
	}

	/**
	 * @param mostrarMensajeResultado
	 *            the mostrarMensajeResultado to set
	 */
	public void setMostrarMensajeResultado(boolean mostrarMensajeResultado) {
		this.mostrarMensajeResultado = mostrarMensajeResultado;
	}

}
