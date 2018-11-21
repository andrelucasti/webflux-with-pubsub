package com.reactivepoc.reactivefluxm.model;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(createIndex = true,indexName = "people", type = "people", shards = 5)
public class People {

    private Integer id;
    private String name;
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
