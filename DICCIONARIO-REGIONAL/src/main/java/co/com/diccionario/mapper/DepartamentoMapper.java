package co.com.diccionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.com.diccionario.document.Departamento;

import co.com.diccionario.dto.DepartamentoDTO;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartamentoMapper {

	DepartamentoMapper INSTANCE = Mappers.getMapper(DepartamentoMapper.class);

	DepartamentoDTO departamentoToDepartamentoDTO(Departamento departamento);

	List<DepartamentoDTO> departamentosToDepartamentoDTOs(List<Departamento> listDepartamento);
	
	Departamento departamentoDTOToDepartamento(DepartamentoDTO departamentoDTO);

}
