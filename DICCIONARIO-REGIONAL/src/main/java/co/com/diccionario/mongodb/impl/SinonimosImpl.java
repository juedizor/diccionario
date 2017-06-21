package co.com.diccionario.mongodb.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import co.com.diccionario.document.Palabras;
import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.mongodb.iface.SinonimosIface;

@Component
public class SinonimosImpl implements SinonimosIface {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<Sinonimos> findByPaisOrigenAndPaisDestinoAndTerminoAndSinonimosIn(String paisOrigen, String paisDestino,
			String termino, Palabras palabraSinonimo) {
		Query query = new Query();
		Criteria criteria = Criteria.where("palabra").is(palabraSinonimo.getPalabra());
		criteria = Criteria.where("pais_origen").is(paisOrigen).and("pais_destino").is(paisDestino)
				.and("termino").is(termino).and("sinonimos").elemMatch(criteria);
		query.addCriteria(criteria);
		List<Sinonimos> listSinonimos = mongoTemplate.find(query, Sinonimos.class);
		return listSinonimos;
	}

}
