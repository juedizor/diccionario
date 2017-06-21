package co.com.diccionario.dto;

import java.io.Serializable;
import java.util.List;

public class SinonimosDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String paisOrigen;
	private String paisDestino;
	private String departamentoOrigen;
	private String departamentoDestino;
	private String ciudadOrigen;
	private String ciudadDestino;
	private String termino;
	private List<String> rutasImagenes;
	private List<byte[]> imagenesBytes;
	private List<String> definiciones;
	private String categoria;
	private List<PalabrasDTO> sinonimos;
	private List<OracionesDTO> oraciones;

	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}

	/**
	 * @return the paisOrigen
	 */
	public String getPaisOrigen() {
		return paisOrigen;
	}

	/**
	 * @param paisOrigen
	 *            the paisOrigen to set
	 */
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	/**
	 * @return the paisDestino
	 */
	public String getPaisDestino() {
		return paisDestino;
	}

	/**
	 * @param paisDestino
	 *            the paisDestino to set
	 */
	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}

	/**
	 * @return the termino
	 */
	public String getTermino() {
		return termino;
	}

	/**
	 * @param termino
	 *            the termino to set
	 */
	public void setTermino(String termino) {
		this.termino = termino;
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
	 * @return the departamentoOrigen
	 */
	public String getDepartamentoOrigen() {
		return departamentoOrigen;
	}

	/**
	 * @param departamentoOrigen
	 *            the departamentoOrigen to set
	 */
	public void setDepartamentoOrigen(String departamentoOrigen) {
		this.departamentoOrigen = departamentoOrigen;
	}

	/**
	 * @return the departamentoDestino
	 */
	public String getDepartamentoDestino() {
		return departamentoDestino;
	}

	/**
	 * @param departamentoDestino
	 *            the departamentoDestino to set
	 */
	public void setDepartamentoDestino(String departamentoDestino) {
		this.departamentoDestino = departamentoDestino;
	}

	/**
	 * @return the ciudadOrigen
	 */
	public String getCiudadOrigen() {
		return ciudadOrigen;
	}

	/**
	 * @param ciudadOrigen
	 *            the ciudadOrigen to set
	 */
	public void setCiudadOrigen(String ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	/**
	 * @return the ciudadDestino
	 */
	public String getCiudadDestino() {
		return ciudadDestino;
	}

	/**
	 * @param ciudadDestino
	 *            the ciudadDestino to set
	 */
	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	/**
	 * @return the definiciones
	 */
	public List<String> getDefiniciones() {
		return definiciones;
	}

	/**
	 * @param definiciones the definiciones to set
	 */
	public void setDefiniciones(List<String> definiciones) {
		this.definiciones = definiciones;
	}

	/**
	 * @return the rutasImagenes
	 */
	public List<String> getRutasImagenes() {
		return rutasImagenes;
	}

	/**
	 * @param rutasImagenes the rutasImagenes to set
	 */
	public void setRutasImagenes(List<String> rutasImagenes) {
		this.rutasImagenes = rutasImagenes;
	}

	/**
	 * @return the imagenesBytes
	 */
	public List<byte[]> getImagenesBytes() {
		return imagenesBytes;
	}

	/**
	 * @param imagenesBytes the imagenesBytes to set
	 */
	public void setImagenesBytes(List<byte[]> imagenesBytes) {
		this.imagenesBytes = imagenesBytes;
	}

	/**
	 * @return the sinonimos
	 */
	public List<PalabrasDTO> getSinonimos() {
		return sinonimos;
	}

	/**
	 * @param sinonimos the sinonimos to set
	 */
	public void setSinonimos(List<PalabrasDTO> sinonimos) {
		this.sinonimos = sinonimos;
	}

	/**
	 * @return the oraciones
	 */
	public List<OracionesDTO> getOraciones() {
		return oraciones;
	}

	/**
	 * @param oraciones the oraciones to set
	 */
	public void setOraciones(List<OracionesDTO> oraciones) {
		this.oraciones = oraciones;
	}
	

}
