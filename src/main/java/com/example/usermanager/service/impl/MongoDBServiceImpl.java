package com.example.usermanager.service.impl;

import com.example.usermanager.domain.CustomerDatabaseSequence;
import com.example.usermanager.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MongoDBServiceImpl implements MongoDBService {


    private final MongoOperations mongoOperations;

    @Autowired
    public MongoDBServiceImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }


    @Override
    public Long generateSequence(String sequenceName) {
        CustomerDatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(sequenceName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                CustomerDatabaseSequence.class);
        return counter.getSeq();
    }
}
