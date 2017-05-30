package co.com.diccionario.mongodb.repository.iface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Paises;

public interface PaisesRepository extends MongoRepository<Paises, Integer> {
	
	List<Paises> findByNombreIgnoreCase(String nombre);

}
