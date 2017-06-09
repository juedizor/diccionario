package co.com.diccionario.negocio.cacheable.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Categoria;
import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.mapper.CategoriaMapper;
import co.com.diccionario.mongodb.repository.iface.CategoriaRepository;
import co.com.diccionario.negocio.cacheable.iface.CategoriasCacheableIface;

@Service
public class CategoriasCacheableImpl implements CategoriasCacheableIface {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	@Cacheable("categorias")
	public List<CategoriaDTO> obtenerCategorias() {
		List<Categoria> listCategoria = categoriaRepository.findAll();
		if (listCategoria != null && !listCategoria.isEmpty()) {
			List<CategoriaDTO> listCatDto = CategoriaMapper.INSTANCE.categoriasToCategoriaDTOs(listCategoria);
			return listCatDto;
		}
		return null;
	}

}
