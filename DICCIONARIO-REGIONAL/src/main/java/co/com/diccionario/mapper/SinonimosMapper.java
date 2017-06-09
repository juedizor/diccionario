package co.com.diccionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.SinonimosDTO;

@Mapper
public interface SinonimosMapper {

	SinonimosMapper INSTANCE = Mappers.getMapper(SinonimosMapper.class);

	SinonimosDTO sinonimosToSinonimoDTO(Sinonimos sinonimos);

	List<SinonimosDTO> sinonimosToSinonimosDTOs(List<Sinonimos> listSinonimos);
	
	String[] sinonimosToSinonimosString(String [] sinonimos);
	
	Sinonimos sinonimoDTOToSinonimo(SinonimosDTO sinonimosDTO);

}
