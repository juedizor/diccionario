package co.com.diccionario.dto;

import java.util.List;

public class PalabrasDTO {
	
	private String palabra;
	private List<Integer> calificacion;
	private Integer promedioCalificacion;
	
	
	/**
	 * @return the palabra
	 */
	public String getPalabra() {
		return palabra;
	}
	/**
	 * @param palabra the palabra to set
	 */
	public void setPalabra(String palabra) {
		this.palabra = palabra;
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
