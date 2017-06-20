package co.com.diccionario.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.com.diccionario.client.catalogos.GestionarPalabrasServiceClient;
import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.dto.ParametrosRegistroTermino;
import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.utils.ParamsBundle;
import co.com.diccionario.utils.Utils;

@ViewScoped
@ManagedBean(name = "registroTermino")
public class RegistroNuevosTerminosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PaisesDTO paisOrigen;
	private List<PaisesDTO> listPaisesOrigen;
	private String nombreBanderaPaisOrigen;

	private PaisesDTO paisDestino;
	private List<PaisesDTO> listPaisesDestino;
	private String nombreBanderaPaisDestino;

	private CategoriaDTO categorias;
	private List<CategoriaDTO> listCategorias;

	private String miTermino;

	private String sinonimoAdd;
	private List<String> listSinonimosAdd;

	private String ejemplo;
	private List<String> ejemplos;

	private String definicion;
	private UploadedFile file;

	private boolean isMostrarAgregarMasContenido;
	private String focusIdLocalizade = "txtPalabraOrigen";

	public RegistroNuevosTerminosMB() {
		// TODO Auto-generated constructor stub
	}
	
	public void upload(FileUploadEvent event) {
		file = event.getFile();
	}

	public void guardarNuevoTermino() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (listSinonimosAdd == null || listSinonimosAdd.isEmpty()) {
			focusIdLocalizade = "txtSinonimoAdd";
			String msg = "Agrega que termino o expresión para el pais " + paisDestino.getNombre();
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		if (ejemplos == null || ejemplos.isEmpty()) {
			focusIdLocalizade = "txtEjemplos";
			String msg = "Agrega ejemplos de los terminos o expresiones para el pais " + paisDestino.getNombre();
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}
		SinonimosDTO sinonimosDTO = new SinonimosDTO();
		sinonimosDTO.setPaisOrigen(paisOrigen.getNombre());
		sinonimosDTO.setPaisDestino(paisDestino.getNombre());
		if (categorias != null) {
			sinonimosDTO.setCategoria(categorias.getNombre());
		}
		sinonimosDTO.setSinonimos(listSinonimosAdd);
		sinonimosDTO.setOraciones(ejemplos);
		sinonimosDTO.setDefiniciones(Arrays.asList(definicion));
		ParametrosRegistroTermino params = new ParametrosRegistroTermino();
		params.setSinonimosDTO(sinonimosDTO);
		if (file != null) {
			params.setImagen(file.getContents());
		}
		try {
			boolean creado = GestionarPalabrasServiceClient.getInstance().crearNuevoTermino(params);
			if (creado) {
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null,
						"Hemos realizado su registro correctamente",
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
				return;
			}
		} catch (Exception e) {
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
			return;
		}

	}

	public void onChangePaisOrigen() {
		if (paisOrigen != null) {
			nombreBanderaPaisOrigen = paisOrigen.getNombre().toLowerCase() + ".png";
		}
	}

	public void onChangePaisDestino() {
		if (paisDestino != null) {
			nombreBanderaPaisDestino = paisDestino.getNombre().toLowerCase() + ".png";
		}
	}

	public void validarPalabraExiste() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (miTermino != null && !miTermino.isEmpty()) {
			miTermino = miTermino.toUpperCase();
			ParamsBusquedaPalabraDTO params = new ParamsBusquedaPalabraDTO();
			params.setPaisOrigen(paisOrigen);
			params.setPaisDestino(paisDestino);
			params.setTermino(miTermino);
			List<SinonimosDTO> listSinonimos;
			try {
				if (categorias != null) {
					params.setCategoria(categorias.getNombre());
					listSinonimos = GestionarPalabrasServiceClient.getInstance()
							.obtenerSinonimosCategoriaPalabra(params);
				} else {
					listSinonimos = GestionarPalabrasServiceClient.getInstance().obtenerSinonimosPalabra(params);
				}
			} catch (Exception e) {
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_ERROR, null, e.getMessage(),
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_error"));
				return;
			}

			if (listSinonimos != null && !listSinonimos.isEmpty()) {
				focusIdLocalizade = "txtPalabraOrigen";
				String msg = "La palabra o expresion " + miTermino + " ya existe para los paises "
						+ paisOrigen.getNombre() + " y " + paisDestino.getNombre() + " ";
				Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
						ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
				return;
			}
		} else {
			focusIdLocalizade = "txtPalabraOrigen";
			String msg = "Debe agregar un temino o expresión";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;

		}
		focusIdLocalizade = "";
	}

	public void convertMayusculaPalabra() {
		if (miTermino != null && !miTermino.isEmpty()) {
			miTermino = miTermino.toUpperCase();
		}
	}

	public void convertMayusculaSinonimoAdd() {
		if (sinonimoAdd != null && !sinonimoAdd.isEmpty()) {
			sinonimoAdd = sinonimoAdd.toUpperCase();
		}
	}

	public void deleteSinonimoTemp() {
		if (sinonimoAdd != null && !sinonimoAdd.isEmpty()) {
			if (listSinonimosAdd.contains(sinonimoAdd)) {
				listSinonimosAdd.remove(sinonimoAdd);
			}
		}
	}

	public void deleteEjemploTemp() {
		if (ejemplo != null && !ejemplo.isEmpty()) {
			if (ejemplos.contains(ejemplo)) {
				ejemplos.remove(ejemplo);
			}
		}
	}

	public void addEjemplosTemp() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (miTermino == null || miTermino.isEmpty()) {
			focusIdLocalizade = "txtPalabraOrigen";
			String msg = "Es obligatorio tener un valor de tu palabra o expresión";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		if (ejemplo == null || ejemplo.isEmpty()) {
			focusIdLocalizade = "txtEjemplos";
			String msg = "Es obligatorio tener un valor";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		if (ejemplos == null || ejemplos.isEmpty()) {
			ejemplos = new ArrayList<>();
		}

		focusIdLocalizade = "txtEjemplos";
		ejemplos.add(ejemplo);
		ejemplo = "";

	}

	public void addSinonimoTemp() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if (miTermino == null || miTermino.isEmpty()) {
			focusIdLocalizade = "txtPalabraOrigen";
			String msg = "Es obligatorio tener un valor de tu palabra o expresión";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		if (sinonimoAdd == null || sinonimoAdd.isEmpty()) {
			focusIdLocalizade = "txtSinonimoAdd";
			String msg = "Es obligatorio tener un valor";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		if (listSinonimosAdd == null || listSinonimosAdd.isEmpty()) {
			listSinonimosAdd = new ArrayList<>();
		}

		if (listSinonimosAdd.contains(sinonimoAdd)) {
			focusIdLocalizade = "txtSinonimoAdd";
			String msg = "La palabra " + sinonimoAdd + " ya la agregaste ";
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null, msg,
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}

		focusIdLocalizade = "txtSinonimoAdd";
		listSinonimosAdd.add(sinonimoAdd);
		sinonimoAdd = "";
	}

	public void eliminarBean() {
		Utils.removeSessionBean("registroTermino");
	}

	/**
	 * @return the isMostrarAgregarMasContenido
	 */
	public boolean isMostrarAgregarMasContenido() {
		return isMostrarAgregarMasContenido;
	}

	/**
	 * @param isMostrarAgregarMasContenido
	 *            the isMostrarAgregarMasContenido to set
	 */
	public void setMostrarAgregarMasContenido(boolean isMostrarAgregarMasContenido) {
		this.isMostrarAgregarMasContenido = isMostrarAgregarMasContenido;
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
	 * @return the miTermino
	 */
	public String getMiTermino() {
		return miTermino;
	}

	/**
	 * @param miTermino
	 *            the miTermino to set
	 */
	public void setMiTermino(String miTermino) {
		this.miTermino = miTermino;
	}

	/**
	 * @return the sinonimoAdd
	 */
	public String getSinonimoAdd() {
		return sinonimoAdd;
	}

	/**
	 * @param sinonimoAdd
	 *            the sinonimoAdd to set
	 */
	public void setSinonimoAdd(String sinonimoAdd) {
		this.sinonimoAdd = sinonimoAdd;
	}

	/**
	 * @return the listSinonimosAdd
	 */
	public List<String> getListSinonimosAdd() {
		return listSinonimosAdd;
	}

	/**
	 * @param listSinonimosAdd
	 *            the listSinonimosAdd to set
	 */
	public void setListSinonimosAdd(List<String> listSinonimosAdd) {
		this.listSinonimosAdd = listSinonimosAdd;
	}

	/**
	 * @return the ejemplo
	 */
	public String getEjemplo() {
		return ejemplo;
	}

	/**
	 * @param ejemplo
	 *            the ejemplo to set
	 */
	public void setEjemplo(String ejemplo) {
		this.ejemplo = ejemplo;
	}

	/**
	 * @return the ejemplos
	 */
	public List<String> getEjemplos() {
		return ejemplos;
	}

	/**
	 * @param ejemplos
	 *            the ejemplos to set
	 */
	public void setEjemplos(List<String> ejemplos) {
		this.ejemplos = ejemplos;
	}

	/**
	 * @return the focusIdLocalizade
	 */
	public String getFocusIdLocalizade() {
		return focusIdLocalizade;
	}

	/**
	 * @param focusIdLocalizade
	 *            the focusIdLocalizade to set
	 */
	public void setFocusIdLocalizade(String focusIdLocalizade) {
		this.focusIdLocalizade = focusIdLocalizade;
	}

	/**
	 * @return the definicion
	 */
	public String getDefinicion() {
		return definicion;
	}

	/**
	 * @param definicion
	 *            the definicion to set
	 */
	public void setDefinicion(String definicion) {
		this.definicion = definicion;
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
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
