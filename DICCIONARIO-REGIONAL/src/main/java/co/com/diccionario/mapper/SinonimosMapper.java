package co.com.diccionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.diccionario.document.Oraciones;
import co.com.diccionario.document.Palabras;
import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.OracionesDTO;
import co.com.diccionario.dto.PalabrasDTO;
import co.com.diccionario.dto.SinonimosDTO;

@Mapper
public interface SinonimosMapper {

	SinonimosMapper INSTANCE = Mappers.getMapper(SinonimosMapper.class);
	
	SinonimosDTO sinonimosToSinonimoDTO(Sinonimos sinonimos);
	
	PalabrasDTO palabraToPalabraDTO(Palabras palabra);

	List<SinonimosDTO> sinonimosToSinonimosDTOs(List<Sinonimos> listSinonimos);
		
	Sinonimos sinonimoDTOToSinonimo(SinonimosDTO sinonimosDTO);
	
	Oraciones oracionesDTOToOraciones(OracionesDTO oraciones);
	
	OracionesDTO oracionesToOracionesDTO(Oraciones oraciones);
	
	Palabras palabraDTOToPalabra(PalabrasDTO palabraDTO);

}
