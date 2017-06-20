package co.com.diccionario.negocio.iface;

import java.util.List;

import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;

public interface GestionarBusquedaPalabrasIface {
	
	public List<SinonimosDTO> obtenerPalabrasPorTerminoCategoria(ParamsBusquedaPalabraDTO params);
	
	public List<SinonimosDTO> buscarPorCategoriaTermino(ParamsBusquedaPalabraDTO params);
	
	public List<SinonimosDTO> obtenerSinonimosPorSinonimo(ParamsBusquedaPalabraDTO params);
	
	public List<SinonimosDTO> buscarPorTermino(ParamsBusquedaPalabraDTO params);

}
