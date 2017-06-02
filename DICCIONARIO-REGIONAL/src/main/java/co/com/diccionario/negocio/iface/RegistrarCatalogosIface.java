package co.com.diccionario.negocio.iface;

import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;

public interface RegistrarCatalogosIface {

	public void registraPaises(PaisesDTO paisesDTO);

	public void registrarDepartamento(DepartamentoDTO departamentoDTO);

	public void registrarCiudad(CiudadDTO ciudadDTO);
	
	public void registrarCategoria(CategoriaDTO categoriaDTO);

}
