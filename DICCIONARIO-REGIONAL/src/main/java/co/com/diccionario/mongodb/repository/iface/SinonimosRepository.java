package co.com.diccionario.mongodb.repository.iface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Sinonimos;

public interface SinonimosRepository extends MongoRepository<Sinonimos, String>{
	

	public List<Sinonimos> findByPaisOrigenAndPaisDestino(String paisOrigen, String paisDestino);
	
}
