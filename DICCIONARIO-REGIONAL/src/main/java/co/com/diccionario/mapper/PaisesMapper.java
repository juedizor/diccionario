package co.com.diccionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.diccionario.document.Paises;
import co.com.diccionario.dto.PaisesDTO;

@Mapper
public interface PaisesMapper {

	PaisesMapper INSTANCE = Mappers.getMapper(PaisesMapper.class);

	PaisesDTO paisesToPaisesDTO(Paises paises);

	List<PaisesDTO> paisesToPaisesDTOs(List<Paises> listPaises);

}
