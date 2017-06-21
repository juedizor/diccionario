package co.com.diccionario.dto;

import java.util.List;

public class ParamsBusquedaPalabraDTO {
	
	
	private PaisesDTO paisOrigen;
	private PaisesDTO paisDestino;
	private DepartamentoDTO departamentoOrigen;
	private DepartamentoDTO departamentoDestino;
	private CiudadDTO ciudadDestino;
	private CiudadDTO ciudadOrigen;
	private String termino;
	private String categoria;
	private List<PalabrasDTO> sinonimos;
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
	/**
	 * @return the departamentoOrigen
	 */
	public DepartamentoDTO getDepartamentoOrigen() {
		return departamentoOrigen;
	}
	/**
	 * @param departamentoOrigen the departamentoOrigen to set
	 */
	public void setDepartamentoOrigen(DepartamentoDTO departamentoOrigen) {
		this.departamentoOrigen = departamentoOrigen;
	}
	/**
	 * @return the departamentoDestino
	 */
	public DepartamentoDTO getDepartamentoDestino() {
		return departamentoDestino;
	}
	/**
	 * @param departamentoDestino the departamentoDestino to set
	 */
	public void setDepartamentoDestino(DepartamentoDTO departamentoDestino) {
		this.departamentoDestino = departamentoDestino;
	}
	/**
	 * @return the ciudadDestino
	 */
	public CiudadDTO getCiudadDestino() {
		return ciudadDestino;
	}
	/**
	 * @param ciudadDestino the ciudadDestino to set
	 */
	public void setCiudadDestino(CiudadDTO ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}
	/**
	 * @return the ciudadOrigen
	 */
	public CiudadDTO getCiudadOrigen() {
		return ciudadOrigen;
	}
	/**
	 * @param ciudadOrigen the ciudadOrigen to set
	 */
	public void setCiudadOrigen(CiudadDTO ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
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
	
	
	
	
}
