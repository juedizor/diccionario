package co.com.diccionario.mongodb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import co.com.diccionario.document.Departamento;
import co.com.diccionario.mongodb.iface.DepartamentoIface;


@Component
public class DepartamentoImpl implements DepartamentoIface {

	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public Departamento findLastId() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "_id"));
		query.limit(1);
		Departamento departamento = mongoTemplate.findOne(query, Departamento.class);
		return departamento;
	}

}
