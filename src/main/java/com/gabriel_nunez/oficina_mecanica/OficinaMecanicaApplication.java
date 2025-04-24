package com.gabriel_nunez.oficina_mecanica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OficinaMecanicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OficinaMecanicaApplication.class, args);
	}

}


