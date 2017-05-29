package co.com.diccionario.mongodb.iface;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.diccionario.document.Departamento;

public interface DepartamentosRepository extends MongoRepository<Departamento, Integer>{
		

}
