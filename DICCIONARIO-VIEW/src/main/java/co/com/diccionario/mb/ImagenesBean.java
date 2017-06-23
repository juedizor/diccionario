package co.com.diccionario.mb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "imagenes")
@ViewScoped
public class ImagenesBean {

	private StreamedContent streamedContent;

	public ImagenesBean() {
	}


	/**
	 * @return the streamedContent
	 * @throws IOException
	 */
	public StreamedContent getStreamedContent() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		String ruta = context.getExternalContext().getRequestParameterMap().get("rutaImagen");
		Path path = Paths.get(ruta);
		InputStream is = new ByteArrayInputStream(Files.readAllBytes(path));
		streamedContent = new DefaultStreamedContent(is, "image/png");
		return streamedContent;
	}

	/**
	 * @param streamedContent
	 *            the streamedContent to set
	 */
	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

}
