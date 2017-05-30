package co.com.diccionario.mongodb.repository.iface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Ciudad;

public interface CiudadesRepository extends MongoRepository<Ciudad, Integer> {

	public List<Ciudad> findByDepartamento(int departamento);

	public List<Ciudad> findByDepartamentoAndNombreIgnoreCase(int departamento, String nombre);

}
