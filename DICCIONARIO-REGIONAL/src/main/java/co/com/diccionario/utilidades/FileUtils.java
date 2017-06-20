package co.com.diccionario.utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

	public static boolean crearDirectorio(String rutaCopia) throws Exception {
		// se genera directorio para la copia del archivo plano y las imagenes
		Path pathAcopiar = Paths.get(rutaCopia);
		boolean pathExits = Files.exists(pathAcopiar);
		if (!pathExits) {
			try {
				Files.createDirectories(pathAcopiar);
			} catch (IOException e) {
				throw new IOException("Error creando directorio de almacenamiento de archivos \n" + e.getMessage());
			}
		}

		return true;
	}

}
