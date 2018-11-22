package com.reactivepoc.reactivefluxm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveFluxMApplication implements CommandLineRunner {

	@Autowired
	private ConsumerStart consumerStart;

	public static void main(String[] args) {

			SpringApplication.run(ReactiveFluxMApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		consumerStart.go();
	}	
}
