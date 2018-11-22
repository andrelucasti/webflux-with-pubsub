package com.reactivepoc.reactivefluxm;

import com.reactivepoc.reactivefluxm.data.PeopleRepository;
import com.reactivepoc.reactivefluxm.data.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ConsumerStart {

    @Autowired
    private ConsumerPeople consumerPeople;

    @Autowired
    private PeopleService peopleService;

    public void go(){

        Flux.
                just(this.consumerPeople.consumer())
                .flatMap(peopleFlux -> peopleFlux)
                .doOnNext(people -> this.peopleService.save(people))
                .subscribe();

    }
}
