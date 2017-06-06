package co.com.diccionario.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sinonimos")
public class Sinonimos {

	@Id
	private String _id;
	@Field (value = "pais_origen")
	private String paisOrigen;
	@Field (value = "pais_destino")
	private String paisDestino;
	private String termino;
	private String categoria;
	private List<String> sinonimos;
	private List<String> oraciones;
	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}
	/**
	 * @param _id the _id to set
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
	 * @param paisOrigen the paisOrigen to set
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
	 * @param paisDestino the paisDestino to set
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
