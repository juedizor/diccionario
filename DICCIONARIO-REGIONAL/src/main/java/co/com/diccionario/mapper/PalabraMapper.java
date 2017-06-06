package co.com.diccionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.SinonimosDTO;

@Mapper
public interface PalabraMapper {

	PalabraMapper INSTANCE = Mappers.getMapper(PalabraMapper.class);

	SinonimosDTO palabraToPalabraDTO(Sinonimos palabra);

	List<SinonimosDTO> palabrasToPalabraDTOs(List<Sinonimos> listPalabras);
	
	Sinonimos palabraDTOToPalabra(SinonimosDTO palabraDTO);

}
