package co.com.diccionario.mongodb.iface;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Paises;

public interface PaisesRepository extends MongoRepository<Paises, Integer> {
	

}
