package co.com.diccionario.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.utils.Utils;

@ViewScoped
@ManagedBean (name = "registroImagen")
public class RegistrarNuevaImagenMB {
	
	private SinonimosDTO selectedSinonimosDTO;
	private UploadedFile file;
	private byte[] bytesImagen;
	
	
	public RegistrarNuevaImagenMB() {
		// TODO Auto-generated constructor stub
	}
	
	public void upload(FileUploadEvent event) {
		file = event.getFile();
		bytesImagen = file.getContents();

	}
	
	public void guardarNuevoImagen(){
		
	}
	
	public void eliminarBean() {
		Utils.removeSessionBean("registroImagen");
	}


	/**
	 * @return the selectedSinonimosDTO
	 */
	public SinonimosDTO getSelectedSinonimosDTO() {
		return selectedSinonimosDTO;
	}



	/**
	 * @param selectedSinonimosDTO the selectedSinonimosDTO to set
	 */
	public void setSelectedSinonimosDTO(SinonimosDTO selectedSinonimosDTO) {
		this.selectedSinonimosDTO = selectedSinonimosDTO;
	}


	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}


	/**
	 * @param file the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}


	/**
	 * @return the bytesImagen
	 */
	public byte[] getBytesImagen() {
		return bytesImagen;
	}


	/**
	 * @param bytesImagen the bytesImagen to set
	 */
	public void setBytesImagen(byte[] bytesImagen) {
		this.bytesImagen = bytesImagen;
	}
	
	
	
	

}
