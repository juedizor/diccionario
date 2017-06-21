package co.com.diccionario.document;

import java.util.List;

public class Palabras {
	
	private String palabra;
	private List<Integer> calificacion;
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
	
	

}
