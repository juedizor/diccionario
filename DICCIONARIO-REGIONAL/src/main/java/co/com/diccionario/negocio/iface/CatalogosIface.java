package co.com.diccionario.negocio.iface;

import java.util.List;

import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;

public interface CatalogosIface {

	public List<PaisesDTO> obtenerPaises();

	public List<DepartamentoDTO> obtenerDepartamentos(int idPais);

	public List<CiudadDTO> obtenerCiudades(int departamento);

}
