package com.reactivepoc.reactivefluxm.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ComponentScan("com.reactivepoc.reactivefluxm")
@EnableElasticsearchRepositories(basePackages = "com.reactivepoc.reactivefluxm.data")
public class DataConfiguration {
}
