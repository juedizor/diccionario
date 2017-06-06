package co.com.diccionario.mongodb.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import co.com.diccionario.document.Categoria;
import co.com.diccionario.mongodb.iface.CategoriaIface;

@Component
public class CategoriaImpl implements CategoriaIface {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<Categoria> findByNombreLike(String nombre) {
		Query query = new Query();
		Criteria criteria = Criteria.where("nombre").regex(nombre);
		query.addCriteria(criteria);
		return mongoTemplate.find(query, Categoria.class);
	}

}
