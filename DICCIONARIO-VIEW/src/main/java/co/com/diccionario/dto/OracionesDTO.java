package co.com.diccionario.dto;

import java.util.List;

public class OracionesDTO {
	
	private String oracion;
	private List<Integer> calificacion;
	private Integer promedioCalificacion;
	
	/**
	 * @return the oracion
	 */
	public String getOracion() {
		return oracion;
	}
	/**
	 * @param oracion the oracion to set
	 */
	public void setOracion(String oracion) {
		this.oracion = oracion;
	}
	/**
	 * @return the calificacion
	 */
	public List<Integer> getCalificacion() {
		return calificacion;
	}
	/**
	 * @param calificacion the calificacion to set
	 */
	public void setCalificacion(List<Integer> calificacion) {
		this.calificacion = calificacion;
	}
	/**
	 * @return the promedioCalificacion
	 */
	public Integer getPromedioCalificacion() {
		return promedioCalificacion;
	}
	/**
	 * @param promedioCalificacion the promedioCalificacion to set
	 */
	public void setPromedioCalificacion(Integer promedioCalificacion) {
		this.promedioCalificacion = promedioCalificacion;
	}
	
	

}
