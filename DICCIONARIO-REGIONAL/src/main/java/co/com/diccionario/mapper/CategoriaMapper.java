package co.com.diccionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.diccionario.document.Categoria;
import co.com.diccionario.dto.CategoriaDTO;

@Mapper
public interface CategoriaMapper {

	CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

	CategoriaDTO categoriaToCategoriaDTO(Categoria categoria);

	List<CategoriaDTO> categoriasToCategoriaDTOs(List<Categoria> listCategoria);

	Categoria categoriDTOToCategoria(CategoriaDTO categoriaDTO);

}
