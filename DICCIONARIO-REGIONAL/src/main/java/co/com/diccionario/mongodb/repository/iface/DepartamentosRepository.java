package co.com.diccionario.mongodb.repository.iface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Departamento;

public interface DepartamentosRepository extends MongoRepository<Departamento, Integer> {

	public List<Departamento> findByPais(int pais);

	public List<Departamento> findByPaisAndNombreIgnoreCase(int idPais, String nombre);

}
