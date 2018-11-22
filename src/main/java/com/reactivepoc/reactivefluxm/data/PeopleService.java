package com.reactivepoc.reactivefluxm.data;

import com.reactivepoc.reactivefluxm.model.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PeopleService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private PeopleRepository peopleRepository;

    @PostConstruct
    public void init() {

        elasticsearchTemplate.createIndex(People.class);
        elasticsearchTemplate.refresh(People.class);
        elasticsearchTemplate.putMapping(People.class);
    }


    public People save(People people){
        return peopleRepository.save(people);

    }


}
