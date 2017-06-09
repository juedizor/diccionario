package co.com.diccionario.negocio.iface;

import java.util.List;

import co.com.diccionario.dto.CategoriaDTO;

public interface GestionarBusquedaCategoriaIface {

	
	public List<CategoriaDTO> obtenerCategorias(String nombre);
	
	public List<CategoriaDTO> obtenerCategoriasAproximado(String nombre);

}
