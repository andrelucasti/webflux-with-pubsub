package com.reactivepoc.reactivefluxm.data;

import com.reactivepoc.reactivefluxm.model.People;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PeopleRepository extends ElasticsearchRepository<People, Integer> {
}
