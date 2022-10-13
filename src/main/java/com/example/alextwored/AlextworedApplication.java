package com.example.alextwored;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class AlextworedApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlextworedApplication.class, args);
	}

}
