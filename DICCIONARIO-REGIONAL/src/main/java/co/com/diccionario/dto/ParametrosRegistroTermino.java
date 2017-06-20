package co.com.diccionario.dto;

public class ParametrosRegistroTermino {
	
	
	private SinonimosDTO sinonimosDTO;
	private byte [] imagen;
	/**
	 * @return the sinonimosDTO
	 */
	public SinonimosDTO getSinonimosDTO() {
		return sinonimosDTO;
	}
	/**
	 * @param sinonimosDTO the sinonimosDTO to set
	 */
	public void setSinonimosDTO(SinonimosDTO sinonimosDTO) {
		this.sinonimosDTO = sinonimosDTO;
	}
	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}
	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	

}
