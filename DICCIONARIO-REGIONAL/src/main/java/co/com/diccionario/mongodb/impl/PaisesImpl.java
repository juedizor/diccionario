package co.com.diccionario.mongodb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import co.com.diccionario.document.Paises;
import co.com.diccionario.mongodb.iface.PaisesIface;

@Component
public class PaisesImpl implements PaisesIface {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Paises findLastId() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "_id"));
		query.limit(1);
		Paises paises = mongoTemplate.findOne(query, Paises.class);
		return paises;
	}

}
