package co.com.diccionario.dto;

import java.io.Serializable;

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
	private String categoria;
	private String[] sinonimos;
	private String[] oraciones;
	private boolean isResultAproximado;

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
	 * @return the sinonimos
	 */
	public String[] getSinonimos() {
		return sinonimos;
	}

	/**
	 * @param sinonimos the sinonimos to set
	 */
	public void setSinonimos(String[] sinonimos) {
		this.sinonimos = sinonimos;
	}

	/**
	 * @return the oraciones
	 */
	public String[] getOraciones() {
		return oraciones;
	}

	/**
	 * @param oraciones the oraciones to set
	 */
	public void setOraciones(String[] oraciones) {
		this.oraciones = oraciones;
	}

	/**
	 * @return the isResultAproximado
	 */
	public boolean isResultAproximado() {
		return isResultAproximado;
	}

	/**
	 * @param isResultAproximado the isResultAproximado to set
	 */
	public void setResultAproximado(boolean isResultAproximado) {
		this.isResultAproximado = isResultAproximado;
	}

}
