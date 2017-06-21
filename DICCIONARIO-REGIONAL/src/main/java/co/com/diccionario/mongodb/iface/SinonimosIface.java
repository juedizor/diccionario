package co.com.diccionario.mongodb.iface;

import java.util.List;

import co.com.diccionario.document.Palabras;
import co.com.diccionario.document.Sinonimos;

public interface SinonimosIface {
	
	
	public List<Sinonimos> findByPaisOrigenAndPaisDestinoAndTerminoAndSinonimosIn(String paisOrigen, String paisDestino,
			String termino, Palabras palabraSinonimo);

}
