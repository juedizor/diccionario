package co.com.diccionario.negocio.cacheable.iface;

import java.util.List;

import co.com.diccionario.dto.CategoriaDTO;

public interface CategoriasCacheableIface {
	
	public List<CategoriaDTO> obtenerCategorias();
	
}
