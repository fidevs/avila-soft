package com.fidev.avilasoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.fidev.avilasoft.repositories")
@EntityScan("com.fidev.avilasoft.entities")
@SpringBootApplication
public class AvilaSoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvilaSoftApplication.class, args);
	}

}
