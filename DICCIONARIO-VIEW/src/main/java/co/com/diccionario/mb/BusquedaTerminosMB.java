package co.com.diccionario.mb;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import co.com.diccionario.client.catalogos.CatalogosServiceClient;
import co.com.diccionario.client.catalogos.GestionarPalabrasServiceClient;
import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
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

	private String idDepartamento;
	private Map<String, String> mapDepartamento;

	private String idDepartamentoDestino;
	private Map<String, String> mapDepartamentoDestino;

	private String idCiudad;
	private Map<String, String> mapCiudad;

	private String idCiudadDestino;
	private Map<String, String> mapCiudadDestino;

	private String idCategoria;
	private Map<String, String> mapCategorias;

	private String pais;
	private String departamento;
	private String ciudad;

	private String idPaisAdd;
	private boolean paisOrigen;

	private String palabra;

	private boolean isMostrarDestino;
	private boolean isMostrarCategoria;
	private boolean isMostrarOrigen = true;
	private boolean isMostrarBtnContinuarDestino = true;
	private boolean isMostrarBotonesDestino;
	private boolean isMostrarBotonesCategoria;
	private boolean isMostrarResultado;

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

		PaisesDTO paisesDestinoDTO = new PaisesDTO();
		valueMap = Utils.foundValueMap(mapPaisesDestino, idPaisDestino);
		paisesDestinoDTO.setNombre(valueMap);
		DepartamentoDTO departamentoOrigenDTO = new DepartamentoDTO();
		DepartamentoDTO departamentoDestinoDTO = new DepartamentoDTO();
		params.setTermino(palabra);
		params.setDepartamentoDestino(departamentoDestinoDTO);
		params.setDepartamentoOrigen(departamentoOrigenDTO);
		params.setPaisDestino(paisesDestinoDTO);
		params.setPaisOrigen(paisesOrigenDTO);

		try {
			listResultadosBusquedaSinonimos = GestionarPalabrasServiceClient.getInstance().obtenerSinonimos(params);
		} catch (Exception e) {
			requestContext.update("msg");
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		int i = 0;
		for (SinonimosDTO sinonimosDTO : listResultadosBusquedaSinonimos) {
			String [] oraciones = sinonimosDTO.getOraciones();
			if(oraciones == null || oraciones.length <= 0){
				oraciones = new String[1];
				oraciones [0] = "No hay ejemplos";
				listResultadosBusquedaSinonimos.get(i).setOraciones(oraciones);
			}
			i++;
		}
		
		requestContext.update("busqueda:fieldPnlResultadosPalabras");
		isMostrarResultado = true;

	}

	public void regresarPanelPaisDestino() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update("busqueda:fieldPnlUbicacion");
		requestContext.update("busqueda:btnContinuarDestino");
		requestContext.update("busqueda:btnsDestino");
		requestContext.update("busqueda:btnsCategoria");
		requestContext.update("busqueda:fieldPnlTerminos");
		requestContext.update("busqueda:fieldPnlResultadosPalabras");
		isMostrarDestino = true;
		isMostrarCategoria = false;
		isMostrarBotonesDestino = true;
		isMostrarBotonesCategoria = false;
		isMostrarResultado = false;
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
		requestContext.update("busqueda:fieldPnlUbicacion");
		requestContext.update("busqueda:btnContinuarDestino");
		requestContext.update("busqueda:btnsDestino");
		requestContext.update("busqueda:btnsCategoria");
		requestContext.update("busqueda:fieldPnlTerminos");
		isMostrarDestino = false;
		isMostrarCategoria = true;
		isMostrarBotonesDestino = false;
		isMostrarBotonesCategoria = true;
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

	public void reloadDepartamentos(String idPaisCargue) {
		cargarDepartamentosDestino(idPaisCargue);
		cargarDepartamentosOrigen(idPaisCargue);
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (paisOrigen) {
			requestContext.update("busqueda:cmbDepartamento");
			if (idPaisCargue.equals(idPaisDestino)) {
				requestContext.update("busqueda:cmbDepartamentoDestino");
			}
		} else {
			requestContext.update("busqueda:cmbDepartamentoDestino");
			if (idPaisCargue.equals(idPais)) {
				requestContext.update("busqueda:cmbDepartamento");
			}
		}

	}

	public void cerrarDlgDepartamento() {
		departamento = "";
	}

	public void cerrarDlgPais() {
		pais = "";
	}

	public void cerrarDlgCiudad() {
		ciudad = "";
	}

	public void cargarDepartamentosDestino(String idPaisDestino) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (idPaisDestino == null || idPaisDestino.trim().isEmpty()) {
			mapDepartamentoDestino = new LinkedHashMap<>();
			mapCiudadDestino = new LinkedHashMap<>();
			idDepartamentoDestino = "-1";
			idCiudadDestino = "-1";
			return;
		}
		List<DepartamentoDTO> listDepartamentoDTO;
		try {
			listDepartamentoDTO = CatalogosServiceClient.getInstance().getDepartamentos(idPaisDestino);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			mapDepartamentoDestino = new LinkedHashMap<>();
			return;
		}

		if (listDepartamentoDTO == null || listDepartamentoDTO.isEmpty()) {
			return;
		}

		mapDepartamentoDestino = new LinkedHashMap<>();
		for (DepartamentoDTO departamentoDTO : listDepartamentoDTO) {
			mapDepartamentoDestino.put(departamentoDTO.getNombre(), "" + departamentoDTO.getId());
		}
	}

	public void cargarDepartamentosOrigen(String idPaisCargar) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (idPaisCargar == null || idPaisCargar.trim().isEmpty()) {
			mapDepartamento = new LinkedHashMap<>();
			mapCiudad = new LinkedHashMap<>();
			idDepartamento = "-1";
			idCiudad = "-1";
			return;
		}
		List<DepartamentoDTO> listDepartamentoDTO;
		try {
			listDepartamentoDTO = CatalogosServiceClient.getInstance().getDepartamentos(idPaisCargar);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			mapDepartamento = new LinkedHashMap<>();
			return;
		}

		if (listDepartamentoDTO == null || listDepartamentoDTO.isEmpty()) {
			return;
		}

		mapDepartamento = new LinkedHashMap<>();
		for (DepartamentoDTO departamentoDTO : listDepartamentoDTO) {
			mapDepartamento.put(departamentoDTO.getNombre(), "" + departamentoDTO.getId());
		}
	}

	public void cargarDepartamentosOrigen(AjaxBehaviorEvent event) {
		String idPaisCargar = (String) event.getComponent().getAttributes().get("idPais");
		cargarDepartamentosOrigen(idPaisCargar);
	}

	public void cargarDepartamentosDestino(AjaxBehaviorEvent event) {
		String idPaisCargar = (String) event.getComponent().getAttributes().get("idPaisDestino");
		cargarDepartamentosDestino(idPaisCargar);
	}

	public void cargarCiudadOrigen() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (idDepartamento == null || idDepartamento.trim().isEmpty()) {
			mapCiudad = new LinkedHashMap<>();
			idCiudad = "-1";
			return;
		}
		List<CiudadDTO> listCiudadDTO;
		try {
			listCiudadDTO = CatalogosServiceClient.getInstance().getCiudades(idDepartamento);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			mapCiudad = new LinkedHashMap<>();
			return;
		}

		if (listCiudadDTO == null || listCiudadDTO.isEmpty()) {
			return;
		}

		mapCiudad = new LinkedHashMap<>();
		for (CiudadDTO ciudadDTO : listCiudadDTO) {
			mapCiudad.put(ciudadDTO.getNombre(), "" + ciudadDTO.getId());
		}
	}

	public void cargarCiudadDestino() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (idDepartamentoDestino == null || idDepartamentoDestino.trim().isEmpty()) {
			mapCiudadDestino = new LinkedHashMap<>();
			idCiudadDestino = "-1";
			return;
		}
		List<CiudadDTO> listCiudadDTO;
		try {
			listCiudadDTO = CatalogosServiceClient.getInstance().getCiudades(idDepartamentoDestino);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			mapCiudadDestino = new LinkedHashMap<>();
			return;
		}

		if (listCiudadDTO == null || listCiudadDTO.isEmpty()) {
			return;
		}

		mapCiudadDestino = new LinkedHashMap<>();
		for (CiudadDTO ciudadDTO : listCiudadDTO) {
			mapCiudadDestino.put(ciudadDTO.getNombre(), "" + ciudadDTO.getId());
		}
	}

	public void paisMayuscula() {
		if (pais != null && !pais.trim().isEmpty()) {
			pais = pais.trim().replaceAll(" +", " ");
			pais = pais.toUpperCase();
		}
	}

	public void departamentoMayuscula() {
		if (departamento != null && !departamento.trim().isEmpty()) {
			departamento = departamento.trim().replaceAll(" +", " ");
			departamento = departamento.toUpperCase();
		}
	}

	public void ciudadMayuscula() {
		if (ciudad != null && !ciudad.trim().isEmpty()) {
			ciudad = ciudad.trim().replaceAll(" +", " ");
			ciudad = ciudad.toUpperCase();
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

	public void addNuevoPais() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (pais == null || pais.trim().isEmpty()) {
			String msg = "El pais es obligatorio";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		/*
		 * se realiza consulta para saber si el nombre digitado ya existe
		 */
		List<PaisesDTO> listPais;
		try {
			listPais = CatalogosServiceClient.getInstance().getPaises(pais);
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		if (listPais != null && !listPais.isEmpty()) {
			String mensaje = "El pais con nombre " + pais + " existe";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, mensaje,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		/*
		 * despues de verificar pais, lo registra
		 */
		PaisesDTO paisDTO = new PaisesDTO();
		paisDTO.setNombre(pais);

		try {
			boolean creado = CatalogosServiceClient.getInstance().crearPais(paisDTO);
			if (creado) {
				obtenerPaises();
				String mensaje = "El pais con nombre " + pais + " ha sido creado";
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null, mensaje,
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
				setPais("");

			}
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

	}

	public void addNuevoDepartamento() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (departamento != null && !departamento.trim().isEmpty()) {
			List<DepartamentoDTO> listDepartamento;
			try {
				listDepartamento = CatalogosServiceClient.getInstance().getDepartamentos(idPaisAdd, departamento);
			} catch (Exception e) {
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, e.getMessage(),
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
				setDepartamento("");
				return;
			}

			if (listDepartamento != null && !listDepartamento.isEmpty()) {
				String namePais = "";
				if (mapPaises != null && !mapPaises.isEmpty()) {
					namePais = Utils.foundValueMap(mapPaises, idPaisAdd);
				}
				String mensaje = "El departamento con nombre " + departamento + " existe para el pais " + namePais;
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, mensaje,
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
				setDepartamento("");
				return;
			}

			DepartamentoDTO departamentoDTO = new DepartamentoDTO();
			departamentoDTO.setPais(Integer.parseInt(idPaisAdd));
			departamentoDTO.setNombre(departamento);
			try {
				boolean creado = CatalogosServiceClient.getInstance().crearDepartamento(departamentoDTO);
				if (creado) {
					reloadDepartamentos(idPaisAdd);
					String namePais = "";
					if (mapPaises != null && !mapPaises.isEmpty()) {
						namePais = Utils.foundValueMap(mapPaises, idPaisAdd);
					}
					String mensaje = "El departamento o provincia con nombre " + departamento
							+ " ha sido creado para el pais " + namePais + "";
					Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_INFO, null, mensaje,
							ParamsBundle.getInstance().getMapMensajes().get("cabecera_info"));
					setDepartamento("");

				}
			} catch (Exception e) {
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, e.getMessage(),
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
				setDepartamento("");
				return;
			}

		}
	}

	public void addNuevaCiudad() {
		if (ciudad == null && !ciudad.trim().isEmpty()) {

		}
	}

	public void changeAddPais(boolean origen) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (origen) {
			if (idPais == null || idPais.trim().isEmpty()) {
				String msg = "Selecciona el pais de donde eres, para asi agregar un nuevo departamento o provincia";
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
				return;
			}
			idPaisAdd = idPais;
			paisOrigen = true;
			requestContext.execute("PF('dlgDepartamento').show();");
		} else {
			if (idPaisDestino == null || idPaisDestino.trim().isEmpty()) {
				String msg = "Selecciona el pais donde te encuentras, para asi agregar un nuevo departamento o provincia";
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
				return;
			}
			idPaisAdd = idPaisDestino;
			paisOrigen = false;
			requestContext.execute("PF('dlgDepartamento').show();");
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
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento
	 *            the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 *            the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the idPaisAdd
	 */
	public String getIdPaisAdd() {
		return idPaisAdd;
	}

	/**
	 * @param idPaisAdd
	 *            the idPaisAdd to set
	 */
	public void setIdPaisAdd(String idPaisAdd) {
		this.idPaisAdd = idPaisAdd;
	}

	/**
	 * @return the paisOrigen
	 */
	public boolean isPaisOrigen() {
		return paisOrigen;
	}

	/**
	 * @param paisOrigen
	 *            the paisOrigen to set
	 */
	public void setPaisOrigen(boolean paisOrigen) {
		this.paisOrigen = paisOrigen;
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

}
