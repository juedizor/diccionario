package co.com.diccionario.mongodb.repository.iface;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Sinonimos;

public interface PalabraRepository extends MongoRepository<Sinonimos, String>{
	

}
