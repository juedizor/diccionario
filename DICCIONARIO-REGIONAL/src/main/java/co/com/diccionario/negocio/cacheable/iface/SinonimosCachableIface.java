package co.com.diccionario.negocio.cacheable.iface;

import java.util.List;

import co.com.diccionario.dto.SinonimosDTO;

public interface SinonimosCachableIface {
	
	public List<SinonimosDTO> obtenerTodasLasPalabras();

}
