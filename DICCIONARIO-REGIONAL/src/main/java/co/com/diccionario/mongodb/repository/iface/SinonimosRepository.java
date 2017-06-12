package co.com.diccionario.mongodb.repository.iface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Sinonimos;

public interface SinonimosRepository extends MongoRepository<Sinonimos, String> {

	public List<Sinonimos> findByPaisOrigenAndPaisDestinoAndDepartamentoOrigenAndDepartamentoDestinoAndTerminoAndCategoria(
			String paisOrigen, String paisDestino, String departamentoOrigen, String departamentoDestino,
			String termino, String categoria);

	public List<Sinonimos> findByPaisOrigenAndPaisDestinoAndTerminoAndCategoria(String paisOrigen, String paisDestino,
			String termino, String categoria);

	public List<Sinonimos> findByPaisOrigenAndPaisDestinoAndTermino(String paisOrigen, String paisDestino,
			String termino);

	public List<Sinonimos> findByPaisOrigenAndPaisDestinoAndCategoria(String paisOrigen, String paisDestino,
			String categoria);

	public List<Sinonimos> findByPaisOrigenAndPaisDestino(String paisOrigen, String paisDestino);
	
	public List<Sinonimos> findByPaisOrigen(String paisOrigen);

}
