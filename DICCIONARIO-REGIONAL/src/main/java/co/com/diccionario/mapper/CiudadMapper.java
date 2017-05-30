package co.com.diccionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.diccionario.document.Ciudad;

import co.com.diccionario.dto.CiudadDTO;

@Mapper
public interface CiudadMapper {

	CiudadMapper INSTANCE = Mappers.getMapper(CiudadMapper.class);

	CiudadDTO ciudadToCiudadDTO(Ciudad ciudad);

	List<CiudadDTO> ciudadesToCiudadDTOs(List<Ciudad> listCiudad);
	
	Ciudad ciudadDTOToCiudad(CiudadDTO ciudadDTO);

}
