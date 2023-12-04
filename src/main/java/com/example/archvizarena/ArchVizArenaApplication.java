package com.example.archvizarena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArchVizArenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchVizArenaApplication.class, args);
	}

}
