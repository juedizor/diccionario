package co.com.diccionario.mb;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import co.com.diccionario.dto.PaisesDTO;

@RequestScoped	
@ManagedBean(name = "registroTermino")
public class RegistroNuevosTerminosMB {
	
	
	private PaisesDTO paisOrigen;
	private List<PaisesDTO> listPaisesOrigen;
	private String nombreBanderaPaisOrigen;
	
	
	private PaisesDTO paisDestino;
	private List<PaisesDTO> listPaisesDestino;
	private String nombreBanderaPaisDestino;
	
	private boolean isMostrarAgregarMasContenido;
	
	
	public RegistroNuevosTerminosMB() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void getParams(){
		System.out.println();
	}


	/**
	 * @return the isMostrarAgregarMasContenido
	 */
	public boolean isMostrarAgregarMasContenido() {
		return isMostrarAgregarMasContenido;
	}


	/**
	 * @param isMostrarAgregarMasContenido the isMostrarAgregarMasContenido to set
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
	 * @param paisOrigen the paisOrigen to set
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
	 * @param listPaisesOrigen the listPaisesOrigen to set
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
	 * @param paisDestino the paisDestino to set
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
	 * @param listPaisesDestino the listPaisesDestino to set
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
	 * @param nombreBanderaPaisOrigen the nombreBanderaPaisOrigen to set
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
	 * @param nombreBanderaPaisDestino the nombreBanderaPaisDestino to set
	 */
	public void setNombreBanderaPaisDestino(String nombreBanderaPaisDestino) {
		this.nombreBanderaPaisDestino = nombreBanderaPaisDestino;
	}
	

}
