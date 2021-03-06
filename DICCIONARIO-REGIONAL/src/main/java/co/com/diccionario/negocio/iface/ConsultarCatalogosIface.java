package co.com.diccionario.negocio.iface;

import java.util.List;

import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;

public interface ConsultarCatalogosIface {

	public List<PaisesDTO> obtenerPaises(String nombre);

	public List<DepartamentoDTO> obtenerDepartamentos(int idPais);

	public List<DepartamentoDTO> obtenerDepartamentos(int idPais, String nombre);

	public List<CiudadDTO> obtenerCiudades(int departamento);

	public List<CiudadDTO> obtenerCiudades(int departamento, String nombre);
	
	

}
