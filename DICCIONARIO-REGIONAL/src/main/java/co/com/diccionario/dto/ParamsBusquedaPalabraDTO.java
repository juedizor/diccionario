package co.com.diccionario.dto;

import java.util.List;

public class ParamsBusquedaPalabraDTO {
	
	
	private PaisesDTO paisOrigen;
	private PaisesDTO paisDestino;
	private String termino;
	private String categoria;
	private List<String> sinonimos;
	private List<String> oraciones;
	
	
	/**
	 * @return the paisOrigen
	 */
	public PaisesDTO getPaisOrigen() {
		return paisOrigen;
	}
	/**
	 * @param paisOrigen the paisOrigen to set
	 */
	public void setPaisOrigen(PaisesDTO paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	/**
	 * @return the paisDestino
	 */
	public PaisesDTO getPaisDestino() {
		return paisDestino;
	}
	/**
	 * @param paisDestino the paisDestino to set
	 */
	public void setPaisDestino(PaisesDTO paisDestino) {
		this.paisDestino = paisDestino;
	}
	/**
	 * @return the termino
	 */
	public String getTermino() {
		return termino;
	}
	/**
	 * @param termino the termino to set
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
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the sinonimos
	 */
	public List<String> getSinonimos() {
		return sinonimos;
	}
	/**
	 * @param sinonimos the sinonimos to set
	 */
	public void setSinonimos(List<String> sinonimos) {
		this.sinonimos = sinonimos;
	}
	/**
	 * @return the oraciones
	 */
	public List<String> getOraciones() {
		return oraciones;
	}
	/**
	 * @param oraciones the oraciones to set
	 */
	public void setOraciones(List<String> oraciones) {
		this.oraciones = oraciones;
	}
}
