package co.com.diccionario.mb;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.utils.ParamsBundle;
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
		/**
		 * envia los datos de la imagen al servicio para ser almacenada 
		 * a la palabra que se esta tratando
		 */
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		if(file == null || bytesImagen == null || bytesImagen.length <= 0){
			Utils.enviarMensajeVista(context, message, FacesMessage.SEVERITY_WARN, null,
					ParamsBundle.getInstance().getMapMensajes().get("msg_error_falta_imagen"),
					ParamsBundle.getInstance().getMapMensajes().get("cabecera_warn"));
			return;
		}
		
		

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
