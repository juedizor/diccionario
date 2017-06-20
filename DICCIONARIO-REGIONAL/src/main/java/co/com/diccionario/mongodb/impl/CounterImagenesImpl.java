package co.com.diccionario.mongodb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import co.com.diccionario.document.CountersImagenes;
import co.com.diccionario.mongodb.iface.CounterImagenesIface;

@Component
public class CounterImagenesImpl implements CounterImagenesIface {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public long getNextSequenceId(String key) {
		Query query = new Query(Criteria.where("_id").is(key));

		Update update = new Update();
		update.inc("seq", 1);

		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		CountersImagenes seqId = mongoTemplate.findAndModify(query, update, options, CountersImagenes.class);

		return seqId.getSeq();
	}

}
