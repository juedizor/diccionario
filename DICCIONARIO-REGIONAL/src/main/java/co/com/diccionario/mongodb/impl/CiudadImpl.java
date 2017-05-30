package co.com.diccionario.mongodb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import co.com.diccionario.document.Ciudad;
import co.com.diccionario.mongodb.iface.CiudadIface;


@Component
public class CiudadImpl implements CiudadIface {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Ciudad findLastId() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "_id"));
		query.limit(1);
		Ciudad ciudad = mongoTemplate.findOne(query, Ciudad.class);
		return ciudad;
	}

}
