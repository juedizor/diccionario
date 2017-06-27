package co.com.diccionario.mb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/images/*")
public class ServletImages extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String rutaImagen = String.valueOf(req.getPathInfo().substring(1));
		Path path = Paths.get(rutaImagen);
		resp.setHeader("Content-Type", getServletContext().getMimeType(path.getFileName().toString()));
		resp.setHeader("Content-Disposition", "inline; filename=\"" + path.getFileName().toString() + "\"");
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			InputStream targetStream = new FileInputStream(rutaImagen);
			input = new BufferedInputStream(targetStream); // Creates buffered
															// input stream.
			output = new BufferedOutputStream(resp.getOutputStream());
			byte[] buffer = new byte[8192];
			for (int length = 0; (length = input.read(buffer)) > 0;) {
				output.write(buffer, 0, length);
			}
		} finally {
			if (output != null)
				output.close();
			if (input != null)
				input.close();
		}
	}

}
