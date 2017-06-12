package co.com.diccionario.negocio.iface;

import java.util.List;

import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;

public interface GestionarBusquedaPalabrasIface {
	
	public List<SinonimosDTO> obtenerPalabrasPorTerminoCategoria(ParamsBusquedaPalabraDTO params);

}
