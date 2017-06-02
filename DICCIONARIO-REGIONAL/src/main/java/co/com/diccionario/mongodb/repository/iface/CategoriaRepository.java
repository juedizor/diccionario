package co.com.diccionario.mongodb.repository.iface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Categoria;

public interface CategoriaRepository extends MongoRepository<Categoria, Integer> {

	public List<Categoria> findByNombreIgnoreCase(String nombre);

}
