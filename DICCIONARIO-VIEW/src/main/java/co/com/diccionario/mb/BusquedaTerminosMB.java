package co.com.diccionario.mb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RateEvent;
import org.primefaces.model.StreamedContent;

import co.com.diccionario.client.catalogos.CatalogosServiceClient;
import co.com.diccionario.client.catalogos.GestionarPalabrasServiceClient;
import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.dto.OracionesDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.dto.PalabrasDTO;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.utils.ParamsBundle;
import co.com.diccionario.utils.Utils;

@ManagedBean(name = "busquedaMB")
@SessionScoped
public class BusquedaTerminosMB {

	private PaisesDTO paisOrigen;
	private List<PaisesDTO> listPaisesOrigen;

	private PaisesDTO paisDestino;
	private List<PaisesDTO> listPaisesDestino;

	private CategoriaDTO categorias;
	private List<CategoriaDTO> listCategorias;

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
	private CategoriaDTO posibleCategoria;
	private List<CategoriaDTO> listPosiblesCategorias;
	private String mensajeCategoriaAproximada;

	private SinonimosDTO selectedSinonimosDTO;
	private List<SinonimosDTO> listResultadosBusquedaSinonimos;
	private String nuevoTermino;

	private boolean mostrarAgregarMiPalabra;
	private boolean mostrarPnlDefinicion;
	
	private StreamedContent streamedContent;

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
		params.setPaisOrigen(paisOrigen);
		params.setPaisDestino(paisDestino);
		params.setCategoria(categoria);
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
			mensajeResultado = "Su consulta a arrojado los siguientes resultados";
			requestContext.update("busqueda:fieldMsgResultado");
			requestContext.update("busqueda:fieldPnlResultadosPalabras");
			requestContext.update("busqueda:pnlAgregarNuevaPalabra");
			mostrarMensajeResultado = true;
			isMostrarResultado = true;
			mostrarAgregarMiPalabra = true;
		} else {
			mensajeResultado = "No hay resultados, con los filtros seleccionados";
			requestContext.update("busqueda:fieldMsgResultado");
			requestContext.update("busqueda:fieldPnlResultadosPalabras");
			requestContext.update("busqueda:pnlAgregarNuevaPalabra");
			mostrarMensajeResultado = true;
			isMostrarResultado = false;
			mostrarAgregarMiPalabra = true;
		}

	}

	public boolean validarMostrarDefinicion(SinonimosDTO result) {
		RequestContext request = RequestContext.getCurrentInstance();
		List<String> definiciones = result.getDefiniciones();
		if (definiciones == null || definiciones.isEmpty()) {
			request.update("busqueda:pnlDefinicion");
			return false;
		} else {
			request.update("busqueda:pnlDefinicion");
			return true;
		}
	}

	public boolean validarMostrarOraciones(SinonimosDTO result) {
		RequestContext request = RequestContext.getCurrentInstance();
		List<OracionesDTO> oraciones = result.getOraciones();
		if (oraciones == null || oraciones.isEmpty()) {
			request.update("busqueda:pnlOraciones");
			return false;
		} else {
			request.update("busqueda:pnlOraciones");
			return true;
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
		requestContext.update("busqueda:pnlAgregarNuevaPalabra");
		mostrarMensajeResultado = false;
		isMostrarCategoria = false;
		isMostrarBotonesCategoria = false;
		isMostrarResultado = false;
		isMostrarBanderas = false;
		mostrarAgregarMiPalabra = false;
		isMostrarDestino = true;
		isMostrarBotonesDestino = true;

	}

	public void continuarPanelCategoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (paisDestino == null) {
			requestContext.update("msg");
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_seleccione_pais"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		nombreBanderaPaisOrigen = paisOrigen.getNombre().toLowerCase() + ".png";
		nombreBanderaPaisDestino = paisDestino.getNombre().toLowerCase() + ".png";

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
		if (paisOrigen == null) {
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

		listCategorias = new ArrayList<>();
		for (CategoriaDTO categoriaDTO : listCategoriaDTO) {
			listCategorias.add(categoriaDTO);
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

		listPaisesOrigen = new ArrayList<>();
		listPaisesDestino = new ArrayList<>();
		for (PaisesDTO paisesDTO : listPaisesDTO) {
			listPaisesOrigen.add(paisesDTO);
			listPaisesDestino.add(paisesDTO);
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

	public void nuevoTerminoMayuscula() {
		if (nuevoTermino != null && !nuevoTermino.trim().isEmpty()) {
			nuevoTermino = nuevoTermino.trim().replaceAll(" +", " ");
			nuevoTermino = nuevoTermino.toUpperCase();
		}
	}

	public void seleccionarCategoriaAproximada() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (posibleCategoria == null) {
			requestContext.update("msg");
			String msg = "Debe seleccionar la categoria";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		requestContext.update("busqueda:cmbCategoria");
		requestContext.update("formAddCategoria");
		requestContext.update("formAddCategoriaAproximadas");
		categorias = posibleCategoria;
		for (CategoriaDTO categoriaDTO : listCategorias) {
			if (categorias.getId().equals(categoriaDTO.getId())) {
				categorias = categoriaDTO;
				break;
			}
		}
		posibleCategoria = null;
		categoria = "";
		requestContext.execute("PF('dlgCategoriaAproximadas').hide();");
		requestContext.execute("PF('dlgCategoria').hide();");
	}

	public void cerrarDialogAddNuevoTermino() {
		setNuevoTermino("");
	}

	public void addNuevoTermino() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();

		/**
		 * primero verifica que el termino no exista
		 */
		ParamsBusquedaPalabraDTO params = new ParamsBusquedaPalabraDTO();
		params.setPaisOrigen(paisOrigen);
		params.setPaisDestino(paisDestino);
		params.setTermino(selectedSinonimosDTO.getTermino());

		if (categorias == null) {
			String msg = "No has seleccionado una categoria";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		params.setCategoria(categorias.getNombre());

		List<PalabrasDTO> listSinonimos = new ArrayList<>();
		PalabrasDTO palabraDTO = new PalabrasDTO();
		palabraDTO.setPalabra(nuevoTermino);
		listSinonimos.add(palabraDTO);
		params.setSinonimos(listSinonimos);

		List<SinonimosDTO> listSinonimosDTO;
		try {
			listSinonimosDTO = GestionarPalabrasServiceClient.getInstance().getSinonimoCategoriaPalabra(params);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			return;
		}

		if (listSinonimosDTO != null && !listSinonimosDTO.isEmpty()) {
			String msg = "La palabra " + nuevoTermino + " ya esta relacionada al termino";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
			return;
		}

		List<PalabrasDTO> sinonimos = selectedSinonimosDTO.getSinonimos();
		if (sinonimos != null && !sinonimos.isEmpty()) {
			palabraDTO = new PalabrasDTO();
			palabraDTO.setPalabra(nuevoTermino);
			sinonimos.add(palabraDTO);
		} else {
			sinonimos = new ArrayList<>();
			palabraDTO = new PalabrasDTO();
			palabraDTO.setPalabra(nuevoTermino);
			sinonimos.add(palabraDTO);
		}

		selectedSinonimosDTO.setSinonimos(sinonimos);
		try {
			selectedSinonimosDTO = GestionarPalabrasServiceClient.getInstance()
					.agregarNuevoSinonimo(selectedSinonimosDTO);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			return;
		}

		String msg = "La palabra " + nuevoTermino + " ha sido relacionada";
		Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null, msg,
				ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
		setNuevoTermino("");
		buscarSinonimos();

	}

	public void changeCalificacionOraciones(RateEvent rateEvent) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		selectedSinonimosDTO = context.getApplication().evaluateExpressionGet(context, "#{result}", SinonimosDTO.class);
		String oracion = context.getApplication().evaluateExpressionGet(context, "#{oracion.oracion}", String.class);
		Iterator<OracionesDTO> iter = selectedSinonimosDTO.getOraciones().iterator();
		int value = ((Integer) rateEvent.getRating()).intValue();
		while (iter.hasNext()) {
			OracionesDTO oracionesDTO = iter.next();
			if (oracionesDTO.getOracion().equals(oracion)) {
				if (oracionesDTO.getCalificacion() != null) {
					oracionesDTO.getCalificacion().add(value);
					break;
				} else {
					List<Integer> calificacion = new ArrayList<>();
					calificacion.add(value);
					oracionesDTO.setCalificacion(calificacion);
					break;
				}
			}
		}

		/*
		 * aqui se consume el servicio enviando selectedSinonimosDTO
		 */
		SinonimosDTO sinonimosDTO;
		try {
			sinonimosDTO = GestionarPalabrasServiceClient.getInstance()
					.actualizarCalificacionOraciones(selectedSinonimosDTO);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			return;
		}
		
		int i = listResultadosBusquedaSinonimos.indexOf(selectedSinonimosDTO);
		iter = listResultadosBusquedaSinonimos.get(i).getOraciones().iterator();
		while (iter.hasNext()) {
			OracionesDTO oracionesDTO = iter.next();
			List<OracionesDTO> listOracionesDTO = sinonimosDTO.getOraciones();
			Iterator<OracionesDTO> iterNew = listOracionesDTO.iterator();
			while (iterNew.hasNext()) {
				OracionesDTO oracionesNew = iterNew.next();
				if (oracionesNew.getOracion().equals(oracionesDTO.getOracion())) {
					oracionesDTO.setCalificacion(oracionesNew.getCalificacion());
					oracionesDTO.setOracion(oracionesNew.getOracion());
					oracionesDTO.setPromedioCalificacion(oracionesNew.getPromedioCalificacion());
				}
			}
		}

		Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null,
				"Su valoración a sido enviada correctamente",
				ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));

	}

	public void changeCalificacion(RateEvent rateEvent) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		selectedSinonimosDTO = context.getApplication().evaluateExpressionGet(context, "#{result}", SinonimosDTO.class);
		String palabra = context.getApplication().evaluateExpressionGet(context, "#{sinonimos.palabra}", String.class);
		Iterator<PalabrasDTO> iter = selectedSinonimosDTO.getSinonimos().iterator();
		int value = ((Integer) rateEvent.getRating()).intValue();
		while (iter.hasNext()) {
			PalabrasDTO palabraDTO = iter.next();
			if (palabraDTO.getPalabra().equals(palabra)) {
				if (palabraDTO.getCalificacion() != null) {
					palabraDTO.getCalificacion().add(value);
					break;
				} else {
					List<Integer> calificacion = new ArrayList<>();
					calificacion.add(value);
					palabraDTO.setCalificacion(calificacion);
					break;
				}
			}
		}

		/*
		 * aqui se consume el servicio enviando selectedSinonimosDTO
		 */
		SinonimosDTO sinonimosDTO;
		try {
			sinonimosDTO = GestionarPalabrasServiceClient.getInstance()
					.actualizarCalificacionSinonimos(selectedSinonimosDTO);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			return;
		}

		int i = listResultadosBusquedaSinonimos.indexOf(selectedSinonimosDTO);
		iter = listResultadosBusquedaSinonimos.get(i).getSinonimos().iterator();
		while (iter.hasNext()) {
			PalabrasDTO palabraDTO = iter.next();
			List<PalabrasDTO> listPalabraDTO = sinonimosDTO.getSinonimos();
			Iterator<PalabrasDTO> iterNew = listPalabraDTO.iterator();
			while (iterNew.hasNext()) {
				PalabrasDTO palabrasNew = iterNew.next();
				if (palabrasNew.getPalabra().equals(palabraDTO.getPalabra())) {
					palabraDTO.setCalificacion(palabrasNew.getCalificacion());
					palabraDTO.setPalabra(palabrasNew.getPalabra());
					palabraDTO.setPromedioCalificacion(palabrasNew.getPromedioCalificacion());
				}
			}
		}

		Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null,
				"Su valoración a sido enviada correctamente",
				ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));

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
	public CategoriaDTO getPosibleCategoria() {
		return posibleCategoria;
	}

	/**
	 * @param posibleCategoria
	 *            the posibleCategoria to set
	 */
	public void setPosibleCategoria(CategoriaDTO posibleCategoria) {
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
	 * @return the listResultados|Sinonimos
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

	/**
	 * @return the selectedSinonimosDTO
	 */
	public SinonimosDTO getSelectedSinonimosDTO() {
		return selectedSinonimosDTO;
	}

	/**
	 * @param selectedSinonimosDTO
	 *            the selectedSinonimosDTO to set
	 */
	public void setSelectedSinonimosDTO(SinonimosDTO selectedSinonimosDTO) {
		this.selectedSinonimosDTO = selectedSinonimosDTO;
	}

	/**
	 * @return the nuevoTermino
	 */
	public String getNuevoTermino() {
		return nuevoTermino;
	}

	/**
	 * @param nuevoTermino
	 *            the nuevoTermino to set
	 */
	public void setNuevoTermino(String nuevoTermino) {
		this.nuevoTermino = nuevoTermino;
	}

	/**
	 * @return the paisOrigen
	 */
	public PaisesDTO getPaisOrigen() {
		return paisOrigen;
	}

	/**
	 * @param paisOrigen
	 *            the paisOrigen to set
	 */
	public void setPaisOrigen(PaisesDTO paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	/**
	 * @return the listPaisesOrigen
	 */
	public List<PaisesDTO> getListPaisesOrigen() {
		return listPaisesOrigen;
	}

	/**
	 * @param listPaisesOrigen
	 *            the listPaisesOrigen to set
	 */
	public void setListPaisesOrigen(List<PaisesDTO> listPaisesOrigen) {
		this.listPaisesOrigen = listPaisesOrigen;
	}

	/**
	 * @return the paisDestino
	 */
	public PaisesDTO getPaisDestino() {
		return paisDestino;
	}

	/**
	 * @param paisDestino
	 *            the paisDestino to set
	 */
	public void setPaisDestino(PaisesDTO paisDestino) {
		this.paisDestino = paisDestino;
	}

	/**
	 * @return the listPaisesDestino
	 */
	public List<PaisesDTO> getListPaisesDestino() {
		return listPaisesDestino;
	}

	/**
	 * @param listPaisesDestino
	 *            the listPaisesDestino to set
	 */
	public void setListPaisesDestino(List<PaisesDTO> listPaisesDestino) {
		this.listPaisesDestino = listPaisesDestino;
	}

	/**
	 * @return the categorias
	 */
	public CategoriaDTO getCategorias() {
		return categorias;
	}

	/**
	 * @param categorias
	 *            the categorias to set
	 */
	public void setCategorias(CategoriaDTO categorias) {
		this.categorias = categorias;
	}

	/**
	 * @return the listCategorias
	 */
	public List<CategoriaDTO> getListCategorias() {
		return listCategorias;
	}

	/**
	 * @param listCategorias
	 *            the listCategorias to set
	 */
	public void setListCategorias(List<CategoriaDTO> listCategorias) {
		this.listCategorias = listCategorias;
	}

	/**
	 * @return the mostrarAgregarMiPalabra
	 */
	public boolean isMostrarAgregarMiPalabra() {
		return mostrarAgregarMiPalabra;
	}

	/**
	 * @param mostrarAgregarMiPalabra
	 *            the mostrarAgregarMiPalabra to set
	 */
	public void setMostrarAgregarMiPalabra(boolean mostrarAgregarMiPalabra) {
		this.mostrarAgregarMiPalabra = mostrarAgregarMiPalabra;
	}

	/**
	 * @return the mostrarPnlDefinicion
	 */
	public boolean isMostrarPnlDefinicion() {
		return mostrarPnlDefinicion;
	}

	/**
	 * @param mostrarPnlDefinicion
	 *            the mostrarPnlDefinicion to set
	 */
	public void setMostrarPnlDefinicion(boolean mostrarPnlDefinicion) {
		this.mostrarPnlDefinicion = mostrarPnlDefinicion;
	}

	/**
	 * @return the streamedContent
	 */
	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	/**
	 * @param streamedContent the streamedContent to set
	 */
	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

}
