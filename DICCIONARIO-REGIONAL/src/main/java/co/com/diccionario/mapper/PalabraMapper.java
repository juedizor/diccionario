package co.com.diccionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.diccionario.document.Palabras;
import co.com.diccionario.dto.PalabrasDTO;

@Mapper
public interface PalabraMapper {
	
	PalabraMapper INSTANCE = Mappers.getMapper(PalabraMapper.class);
	
	
	List<Palabras> palabrasToPalabrasDTO(List<PalabrasDTO> listPalabra);
	
	PalabrasDTO palabraToPalabraDTO(Palabras palabra);
	
	Palabras palabraDTOToPalabra (PalabrasDTO palabra);

}
